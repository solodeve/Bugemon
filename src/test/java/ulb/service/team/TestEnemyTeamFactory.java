package ulb.service.team;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.team.Team;
import ulb.repository.GameRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEnemyTeamFactory {

    @Test
    public void testCreateProducesTeamWithinSizeAndVariesWithSeed() throws Exception {
        DatabaseBugemon db = new DatabaseBugemon();
        Assumptions.assumeTrue(isDatabaseAvailable(db), "Skipping: database not reachable.");

        long seed1 = 321;
        long seed2 = 123;

        GameRepository repository = new GameRepository();
        repository.initialize();

        EnemyTeamFactory factory = new EnemyTeamFactory(repository);
        Team team1 = factory.create(3, seed1);
        Team team2 = factory.create(3, seed2);

        assertTrue(!team1.isEmpty() && team1.size() <= 3);
        assertTrue(!team2.isEmpty() && team2.size() <= 3);
        assertNotEquals(team1, team2);
    }

    private static boolean isDatabaseAvailable(DatabaseBugemon db) {
        if (!db.isConfigured()) return false;
        try (Connection ignored = db.connect()) {
            db.executeSeedFiles();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
