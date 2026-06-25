package ulb.repository;

import ulb.exception.TeamPersistenceException;
import ulb.model.domain.GameMode;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.team.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/** Database-backed implementation of {@link ITeamRepository}. */
public class TeamRepository implements ITeamRepository {

    private final DatabaseBugemon databaseBugemon;
    private final TeamLoader loader;
    private final TeamSaver saver;

    public TeamRepository(String teamType, Integer userId) {
        String normalized = normalizeTeamType(teamType);
        int uid = userId == null ? 0 : userId;
        this.databaseBugemon = new DatabaseBugemon();
        this.loader = new TeamLoader(normalized, uid);
        this.saver = new TeamSaver(normalized, uid);
    }

    @Override
    public List<Team> loadTeams(IBugemonCatalogRepository catalog) throws TeamPersistenceException {
        try (Connection conn = databaseBugemon.connect()) {
            return loader.load(conn, catalog);
        } catch (SQLException e) {
            throw new TeamPersistenceException("Unable to load saved teams from database.", e);
        }
    }

    /**
     * Persists {@code teams} within a transaction; rolls back on failure.
     *
     * @throws TeamPersistenceException if the transaction cannot be completed
     */
    @Override
    public void saveTeams(List<Team> teams) throws TeamPersistenceException {
        try (Connection conn = databaseBugemon.connect()) {
            conn.setAutoCommit(false);
            try {
                saver.save(conn, teams);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new TeamPersistenceException("Unable to save teams to database.", e);
        }
    }

    @Override
    public void deleteTeamByName(String name) throws TeamPersistenceException {
        try (Connection conn = databaseBugemon.connect()) {
            saver.deleteByName(conn, name);
        } catch (SQLException e) {
            throw new TeamPersistenceException("Unable to delete team by name.", e);
        }
    }

    private static String normalizeTeamType(String rawType) {
        if (rawType == null) return GameMode.FREE_BATTLE.name();
        String normalized = rawType.trim();
        if (normalized.isEmpty()) return GameMode.FREE_BATTLE.name();
        return normalized.toUpperCase(Locale.ROOT);
    }
}
