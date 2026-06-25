package ulb.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import ulb.configuration.SQLQueries;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Level;
import ulb.model.domain.Statistics;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.no.FloorDefinition;
import ulb.model.team.Team;

/**
 * Loads and materializes NO tower floor definitions from the database.
 *
 * <p>Depends on {@link IBugemonCatalogRepository} to resolve Bugemon and skill references.
 * Call {@link #load()} once after construction before using any query methods.</p>
 */
public class FloorRepository implements IFloorRepository {

    private static final int MAX_ENEMY_TEAMS_PER_FLOOR = 3;

    private record StoredTeamDefinition(String name, List<Bugemon> availableMembers, int numberOfBugemon) {}
    private record StoredFloorDefinition(int floorNumber, List<StoredTeamDefinition> enemyTeams,
                                         StoredTeamDefinition bossTeam, String bossName, String bossSpritePath) {}

    private final Map<Integer, StoredFloorDefinition> floors = new HashMap<>();
    private final IBugemonCatalogRepository catalog;
    private final DatabaseBugemon databaseBugemon;

    public FloorRepository(IBugemonCatalogRepository catalog, DatabaseBugemon databaseBugemon) {
        this.catalog = catalog;
        this.databaseBugemon = databaseBugemon;
    }

    /** Clears and reloads all floor definitions from the database. */
    public void load() throws IOException {
        if (!databaseBugemon.isConfigured()) {
            throw new IOException("Database is not configured. Cannot load floor definitions.");
        }
        floors.clear();
        try {
            loadFromDatabase();
        } catch (SQLException e) {
            throw new IOException("Failed to load floor definitions from database.", e);
        }
    }

    @Override
    public Optional<FloorDefinition> getFloorByNumber(int floorNumber) {
        StoredFloorDefinition floor = floors.get(floorNumber);
        return floor == null ? Optional.empty() : Optional.of(copyFloorDefinition(floor));
    }

    @Override
    public List<FloorDefinition> getAllFloors() {
        return floors.values().stream()
                .sorted(Comparator.comparingInt(StoredFloorDefinition::floorNumber))
                .map(this::copyFloorDefinition)
                .toList();
    }

    @Override
    public int getNumbersOfFloors() { return floors.size(); }

    private void loadFromDatabase() throws SQLException {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_FLOOR_TEAMS);
             ResultSet rs = stmt.executeQuery()) {

            Map<Integer, List<StoredTeamDefinition>> enemyTeamsByFloor = new HashMap<>();
            Map<Integer, StoredTeamDefinition> bossTeamByFloor = new HashMap<>();

            while (rs.next()) {
                int teamId      = rs.getInt("id");
                int floorNum    = rs.getInt("floor_numero");
                boolean isBoss  = rs.getBoolean("is_boss");
                String nom      = rs.getString("nom");
                Object rawCount = rs.getObject("nombre_bugemons");
                int count       = rawCount != null ? rs.getInt("nombre_bugemons") : 0;
                List<Bugemon> members = loadFloorTeamBugemons(conn, teamId);
                int effective = count > 0 ? Math.min(count, members.size()) : members.size();
                StoredTeamDefinition team = new StoredTeamDefinition(nom, List.copyOf(members), effective);

                if (isBoss) {
                    bossTeamByFloor.put(floorNum, team);
                } else {
                    List<StoredTeamDefinition> enemies = enemyTeamsByFloor.computeIfAbsent(floorNum, k -> new ArrayList<>());
                    if (enemies.size() < MAX_ENEMY_TEAMS_PER_FLOOR) enemies.add(team);
                }
            }

            Set<Integer> allFloorNums = new HashSet<>();
            allFloorNums.addAll(enemyTeamsByFloor.keySet());
            allFloorNums.addAll(bossTeamByFloor.keySet());
            for (int floorNum : allFloorNums) {
                List<StoredTeamDefinition> enemies = enemyTeamsByFloor.getOrDefault(floorNum, List.of());
                StoredTeamDefinition boss = bossTeamByFloor.getOrDefault(floorNum, new StoredTeamDefinition("", List.of(), 0));
                floors.put(floorNum, new StoredFloorDefinition(floorNum, List.copyOf(enemies), boss, boss.name(), ""));
            }
        }
    }

    private List<Bugemon> loadFloorTeamBugemons(Connection conn, int teamId) throws SQLException {
        List<Bugemon> members = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_FLOOR_TEAM_BUGEMONS)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bugemonId = rs.getString("bugemon_id");
                    Optional<Bugemon> baseOpt = catalog.getBugemonById(bugemonId);
                    if (baseOpt.isEmpty()) continue;
                    Bugemon b = baseOpt.get().copy();
                    int niveau = rs.getInt("niveau");
                    b.setLevel(new Level(niveau, rs.getInt("xp")));
                    for (int l = 1; l <= niveau; l++) catalog.getLevelRewardByLevel(l).ifPresent(b::learnSkill);
                    Integer hp = (Integer) rs.getObject("hp");
                    if (hp != null) {
                        Statistics stats = new Statistics(hp, rs.getInt("attaque"), rs.getInt("defense"),
                                rs.getInt("initiative"), rs.getInt("defense_magic"), rs.getInt("attack_magic"));
                        Integer maxHp = (Integer) rs.getObject("max_hp");
                        if (maxHp != null) stats.setMaxHp(maxHp);
                        b.setStats(stats);
                    }
                    members.add(b);
                }
            }
        }
        return members;
    }

    private FloorDefinition copyFloorDefinition(StoredFloorDefinition floor) {
        List<Team> copiedEnemyTeams = floor.enemyTeams().stream().map(this::materializeTeam).toList();
        return new FloorDefinition(floor.floorNumber(), copiedEnemyTeams, materializeTeam(floor.bossTeam()),
                floor.bossName(), floor.bossSpritePath());
    }

    private Team materializeTeam(StoredTeamDefinition source) {
        Team team = new Team(source == null ? "" : source.name());
        if (source == null) return team;
        List<Bugemon> pool = new ArrayList<>(source.availableMembers());
        Collections.shuffle(pool);
        int size = Math.min(source.numberOfBugemon(), pool.size());
        for (int i = 0; i < size; i++) {
            Bugemon member = pool.get(i);
            if (member != null) team.add(member.copy());
        }
        return team;
    }
}
