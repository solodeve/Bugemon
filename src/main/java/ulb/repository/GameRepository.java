package ulb.repository;

import java.io.IOException;
import java.util.*;

import ulb.exception.TeamPersistenceException;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Skill;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.infrastructure.UserDao;
import ulb.model.inventory.Item;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.no.FloorDefinition;
import ulb.model.special.DomainExpansion;
import ulb.model.team.Team;
import ulb.model.user.User;

/**
 * Facade implementing {@link IGameRepository} by delegating to focused sub-repositories.
 */
public class GameRepository implements IGameRepository {

    private final DatabaseBugemon databaseBugemon = new DatabaseBugemon();
    private final BugemonCatalogRepository catalog = new BugemonCatalogRepository(databaseBugemon);
    private final FloorRepository floors = new FloorRepository(catalog, databaseBugemon);
    private final UnlockRepository unlocks = new UnlockRepository(databaseBugemon);
    private final UserDao userDao = new UserDao(databaseBugemon);
    private final LeaderboardRepository leaderboardRepository = new LeaderboardRepository();
    private final User user = new User();

    private static final List<String> SPECIAL_BUGEMON_IDS = List.of("lichenox", "inferlin", "finalboss");

    public GameRepository() {
    }

    /**
     * Loads all game data required to play from the database.
     * Skills must be loaded before Bugemons so skill references can be resolved.
     */
    public void initialize() throws IOException {
        initialize(new User());
    }

    public void initialize(User user) throws IOException {
        catalog.load();
        floors.load();
        unlocks.clear();
        setCurrentUser(user);
    }

    public void loadFloors() throws IOException {
        floors.load();
    }

    /**
     * Sets the authenticated user as the active session user and refreshes unlock state.
     *
     * @param currentUser the user to activate; may be {@code null} for anonymous
     */
    public void setCurrentUser(User currentUser) {
        unlocks.clear();
        if (currentUser == null) {
            this.user.setInformation(null, null);
            reloadUnlockedBugemons();
            return;
        }
        Integer currentUserId = currentUser.getId();
        if (currentUserId != null && databaseBugemon.isConfigured()) {
            Optional<User> refreshed = userDao.findById(currentUserId);
            if (refreshed.isPresent()) {
                User resolved = refreshed.get();
                this.user.setInformation(resolved.getUsername(), resolved.getId());
                reloadUnlockedBugemons();
                return;
            }
        }
        this.user.setInformation(currentUser.getUsername(), currentUser.getId());
        reloadUnlockedBugemons();
    }

    private void reloadUnlockedBugemons() {
        Set<String> knownIds = new HashSet<>();
        catalog.getAllBugemons().forEach(b -> knownIds.add(b.getId()));
        unlocks.loadForUser(user.getId(), knownIds);
    }

    public Integer getCurrentUserId() { return user.getId(); }

    @Override
    public String getCurrentUsername() { return user.getUsername(); }

    @Override
    public User getUser() { return user; }

    @Override public List<Bugemon> getAllBugemons() { return catalog.getAllBugemons(); }

    @Override public List<Bugemon> getAllBugemonsAtLevel(int level) { return catalog.getAllBugemonsAtLevel(level); }

    @Override public Optional<Bugemon> getBugemonById(String id) { return catalog.getBugemonById(id); }

    @Override public boolean containsBugemon(String bugemonId) { return catalog.containsBugemon(bugemonId); }

    @Override public boolean isBugemonUnlocked(String bugemonId) { return unlocks.isBugemonUnlocked(bugemonId); }

    @Override
    public boolean unlockBugemon(String bugemonId) {
        if (!catalog.containsBugemon(bugemonId)) return false;
        return unlocks.unlockBugemon(bugemonId);
    }

    @Override
    public List<Bugemon> unlockBugemonsFromTeam(Team team) {
        if (team == null) return List.of();
        List<Bugemon> newlyUnlocked = new ArrayList<>();
        for (Bugemon member : team.getMembers()) {
            if (member == null || SPECIAL_BUGEMON_IDS.contains(member.getId())) continue;
            unlockAndCollect(member.getId(), newlyUnlocked);
        }
        return newlyUnlocked;
    }

    @Override
    public List<Bugemon> unlockBugemonsOnFloorClear(int floorNumber) {
        List<Bugemon> newlyUnlocked = new ArrayList<>();
        switch (floorNumber) {
            case 1 -> unlockAndCollect("lichenox", newlyUnlocked);
            case 2 -> unlockAndCollect("inferlin", newlyUnlocked);
            case 9 -> unlockAndCollect("finalboss", newlyUnlocked);
            default -> { /* no reward for this floor */ }
        }
        return newlyUnlocked;
    }

    private void unlockAndCollect(String bugemonId, List<Bugemon> newlyUnlocked) {
        if (unlockBugemon(bugemonId)) catalog.getBugemonById(bugemonId).ifPresent(newlyUnlocked::add);
    }

    @Override public Optional<Skill> getSkillById(String id) { return catalog.getSkillById(id); }

    @Override public Optional<Skill> getLevelRewardByLevel(int level) { return catalog.getLevelRewardByLevel(level); }
    @Override public Map<Integer, Skill> getAllLevelRewards()         { return catalog.getAllLevelRewards(); }

    @Override public Optional<Item> getItemById(String id) { return catalog.getItemById(id); }

    @Override public Optional<DomainExpansion> getDomainExpansionById(String id) { return catalog.getDomainExpansionById(id); }

    @Override public Map<Item, Integer> getStartingInventory() { return catalog.getStartingInventory(); }

    @Override public int getNumbersOfFloors() { return floors.getNumbersOfFloors(); }

    @Override public List<FloorDefinition> getAllFloors() { return floors.getAllFloors(); }

    @Override public Optional<FloorDefinition> getFloorByNumber(int floorNumber) { return floors.getFloorByNumber(floorNumber); }

    /**
     * Saves the team, replacing the existing team with the same name or adding it if none exists.
     *
     * @throws TeamPersistenceException if the team cannot be persisted
     */
    @Override
    public void saveTeams(Team team, String teamType) throws TeamPersistenceException {
        TeamRepository teamRepository = new TeamRepository(teamType, user.getId());
        List<Team> existingTeams = teamRepository.loadTeams(this);
        boolean replaced = false;
        for (int i = 0; i < existingTeams.size(); i++) {
            if (existingTeams.get(i).hasName(team.getName())) {
                existingTeams.set(i, team);
                replaced = true;
                break;
            }
        }
        if (!replaced) existingTeams.add(team);
        teamRepository.saveTeams(existingTeams);
    }

    /**
     * Saves the entry, using the entry's user ID if set, otherwise falling back to the current session user.
     *
     * @throws IllegalStateException if no valid user ID can be resolved
     */
    @Override
    public void saveLeaderboardEntry(LeaderboardEntry leaderboardEntry) {
        User entryUser = leaderboardEntry == null ? null : leaderboardEntry.user();
        Integer currentUserId = entryUser == null ? user.getId() : entryUser.getId();
        if (currentUserId == null || currentUserId <= 0) {
            throw new IllegalStateException("Cannot save leaderboard entry without an authenticated user.");
        }
        leaderboardRepository.saveEntry(currentUserId, leaderboardEntry);
    }

    @Override
    public List<LeaderboardEntry> loadLeaderboard() {
        return leaderboardRepository.loadTopEntries();
    }
}
