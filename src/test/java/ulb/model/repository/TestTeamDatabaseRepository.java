package ulb.model.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.configuration.SQLQueries;
import ulb.model.domain.Bugemon;
import ulb.model.domain.Level;
import ulb.exception.TeamPersistenceException;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.team.Team;
import ulb.model.user.User;
import ulb.repository.GameRepository;
import ulb.repository.TeamRepository;
import ulb.model.domain.GameMode;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestTeamDatabaseRepository {
    private static final int TEST_USER_ID = 999999;

    @BeforeEach
    public void setup() {
        DatabaseBugemon db = new DatabaseBugemon();
        Assumptions.assumeTrue(isDatabaseAvailable(db), "Skipping DB tests: Neon is not configured or reachable.");
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.INSERT_USER_CONFLICT)) {
            stmt.setInt(1, TEST_USER_ID);
            stmt.setString(2, "test_user");
            stmt.executeUpdate();
        } catch (SQLException e) {
            Assumptions.assumeTrue(false, "Skipping DB tests: database setup failed.");
        }
    }

    private boolean isDatabaseAvailable(DatabaseBugemon db) {
        if (!db.isConfigured()) {
            return false;
        }

        try (Connection ignored = db.connect()) {
            db.executeSeedFiles();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private Team createForTesting(List<Bugemon> bugemons, String teamName) {
        Team team = new Team(teamName);
        Bugemon b1 = bugemons.get(0);
        Bugemon b2 = bugemons.get(1);
        b1.setLevel(new Level(7, 2));
        b2.setLevel(new Level(7, 2));
        team.add(b1);
        team.add(b2);
        return team;
    }

    private boolean isSameTeam(Team team1, Team team2) {
        List<Bugemon> members1 = team1.getMembers();
        List<Bugemon> members2 = team2.getMembers();
        for (int i = 0; i < members1.size(); i++) {
            Bugemon b1 = members1.get(i);
            Bugemon b2 = members2.get(i);
            if (!b1.getId().equals(b2.getId())
                    || b1.getXP() != b2.getXP()
                    || b1.getLevel() != b2.getLevel()) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testStoreTeamProperly() throws TeamPersistenceException, IOException {
        GameRepository gameRepository = new GameRepository();
        gameRepository.initialize();

        TeamRepository repo = new TeamRepository(
                GameMode.FREE_BATTLE.name(), TEST_USER_ID);

        List<Bugemon> bugemons = gameRepository.getAllBugemons();
        Team teamToSave = createForTesting(bugemons, "test team");

        repo.saveTeams(List.of(teamToSave));

        List<Team> storedTeams = repo.loadTeams(gameRepository);
        assertFalse(storedTeams.isEmpty(), "No teams were loaded after saving.");
        Team firstStoredTeam = storedTeams.get(0);
        assertTrue(isSameTeam(teamToSave, firstStoredTeam));

        Optional<Bugemon> firstSavedBugemonOptional = teamToSave.getTeamMember(0);
        Bugemon firstSavedBugemon = firstSavedBugemonOptional.orElseThrow();
        String firstSavedBugemonId = firstSavedBugemon.getId();

        Optional<Bugemon> catalogBugemonOptional = gameRepository.getBugemonById(firstSavedBugemonId);
        Bugemon catalogBugemon = catalogBugemonOptional.orElseThrow();

        Optional<Bugemon> loadedBugemonOptional = firstStoredTeam.getTeamMember(0);
        Bugemon loadedBugemon = loadedBugemonOptional.orElseThrow();
        assertNotSame(catalogBugemon, loadedBugemon, "Loaded team members must not reuse catalog bugemon instances.");

        repo.deleteTeamByName("test team");
    }

    @Test
    public void testStoreTeamsByTypeSeparately() throws TeamPersistenceException, IOException {
        GameRepository gameRepository = new GameRepository();
        gameRepository.initialize();

        List<Bugemon> bugemons = gameRepository.getAllBugemons();

        Team freeBattleTeam = createForTesting(bugemons, "free team");
        Team noTowerTeam    = createForTesting(bugemons, "no team");

        TeamRepository freeRepo = new TeamRepository("FREE_BATTLE", TEST_USER_ID);
        TeamRepository noRepo   = new TeamRepository("NO_TOWER",    TEST_USER_ID);

        freeRepo.saveTeams(List.of(freeBattleTeam));
        noRepo.saveTeams(List.of(noTowerTeam));

        List<Team> loadedFree = freeRepo.loadTeams(gameRepository);
        List<Team> loadedNo   = noRepo.loadTeams(gameRepository);

        assertEquals(1, loadedFree.size());
        assertEquals("free team", loadedFree.getFirst().getName());

        assertEquals(1, loadedNo.size());
        assertEquals("no team", loadedNo.getFirst().getName());

        freeRepo.deleteTeamByName("free team");
        noRepo.deleteTeamByName("no team");
    }

    @Test
    public void testNoTowerSaveDoesNotResetLeveledBugemonBetweenRooms() throws TeamPersistenceException, IOException {
        GameRepository gameRepository = new GameRepository();
        User testUser = new User(TEST_USER_ID, "test_user");
        gameRepository.initialize(testUser);

        TeamRepository noTowerRepository = new TeamRepository("NO_TOWER", TEST_USER_ID);
        Team towerTeam = new Team("tower-level-regression");
        List<Bugemon> catalogBugemons = gameRepository.getAllBugemons();
        Bugemon firstMember = catalogBugemons.get(0);
        towerTeam.add(firstMember);
        noTowerRepository.saveTeams(List.of(towerTeam));

        firstMember.setLevel(new Level(5, 0));
        gameRepository.saveTeams(towerTeam, "NO_TOWER");

        assertEquals(5, firstMember.getLevel(), "In-memory level should not be reset when saving NO tower progress.");

        List<Team> storedNoTowerTeams = noTowerRepository.loadTeams(gameRepository);
        Team storedTowerTeam = storedNoTowerTeams.stream()
                .filter(team -> team.hasName("tower-level-regression"))
                .findFirst()
                .orElseThrow();
        Optional<Bugemon> storedFirstMemberOptional = storedTowerTeam.getTeamMember(0);
        Bugemon storedFirstMember = storedFirstMemberOptional.orElseThrow();
        assertEquals(5, storedFirstMember.getLevel(), "Saved NO tower team should keep the gained level.");

        noTowerRepository.deleteTeamByName("tower-level-regression");
    }

    @AfterEach
    public void teardown() throws Exception {
        DatabaseBugemon db = new DatabaseBugemon();
        if (!db.isConfigured()) return;
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     SQLQueries.DELETE_USER)) {
            stmt.setInt(1, TEST_USER_ID);
            stmt.executeUpdate();
        }
    }
}
