package ulb.repository;

import ulb.configuration.SQLQueries;
import ulb.model.infrastructure.DatabaseBugemon;
import ulb.model.leaderboard.LeaderboardEntry;
import ulb.model.leaderboard.TowerRunStat;
import ulb.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Persists and retrieves leaderboard entries from the database. */
public final class LeaderboardRepository implements ILeaderboardRepository {
    private static final int DEFAULT_LIMIT = 50;
    private final DatabaseBugemon databaseBugemon = new DatabaseBugemon();
    private record BestEntry(int id, int score) {}

    public LeaderboardRepository() {
    }

    private void insertLeaderboardEntry(Connection connection, int playerId, LeaderboardEntry entry) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERT_LEADERBOARD_ENTRY)) {
            TowerRunStat runStat = resolveRunStat(entry);
            bindInsertStatement(statement, playerId, entry.score(), runStat);
            statement.executeUpdate();
        }
    }

    private BestEntry findBestEntry(Connection connection, int playerId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_LEADERBOARD_BEST_FOR_PLAYER)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return new BestEntry(resultSet.getInt("id"), resultSet.getInt("score"));
            }
        }
    }

    private void updateLeaderboardEntry(Connection connection, int entryId, LeaderboardEntry entry) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATE_LEADERBOARD_ENTRY)) {
            TowerRunStat runStat = resolveRunStat(entry);
            bindUpdateStatement(statement, entryId, entry.score(), runStat);
            statement.executeUpdate();
        }
    }

    /**
     * Saves a leaderboard entry for the given player.
     * Stores the entry only when it improves the player's best score.
     *
     * @param playerId the authenticated player's ID; must be positive
     * @param entry    the entry to persist; must not be {@code null}
     */
    public void saveEntry(int playerId, LeaderboardEntry entry) {
        if (!databaseBugemon.isConfigured()) {
            return;
        }
        validateSaveEntry(playerId, entry);

        try (Connection connection = databaseBugemon.connect()) {
            saveEntry(connection, playerId, entry);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save leaderboard entry.", e);
        }
    }

    /**
     * Loads the top {@value #DEFAULT_LIMIT} leaderboard entries.
     *
     * @return the top entries; empty list if the database is not configured
     */
    public List<LeaderboardEntry> loadTopEntries() {
        return loadTopEntries(DEFAULT_LIMIT);
    }

    private void extractEntries(ResultSet resultSet, List<LeaderboardEntry> entries) throws SQLException {
        while (resultSet.next()) {
            entries.add(new LeaderboardEntry(
                    new User(resultSet.getInt("player_id"), resultSet.getString("name")),
                    null,
                    resultSet.getInt("score"),
                    new TowerRunStat(
                            resultSet.getInt("floor_reached"),
                            resultSet.getInt("combats_won"),
                            resultSet.getInt("bugemons_defeated"),
                            resultSet.getInt("bugemons_lost"),
                            resultSet.getInt("flawless_floors")
                    )
            ));
        }
    }

    private List<LeaderboardEntry> extractEntries(int resolvedLimit) {
        List<LeaderboardEntry> entries = new ArrayList<>();

        try (Connection connection = databaseBugemon.connect();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_LEADERBOARD_TOP)) {
            statement.setInt(1, resolvedLimit);

            try (ResultSet resultSet = statement.executeQuery()) {
                extractEntries(resultSet, entries);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load leaderboard entries.", e);
        }
        return entries;
    }

    /**
     * Loads up to {@code limit} top leaderboard entries.
     *
     * @param limit maximum number of entries to return; clamped to at least 1
     * @return the top entries; empty list if the database is not configured
     */
    public List<LeaderboardEntry> loadTopEntries(int limit) {
        if (!databaseBugemon.isConfigured()) {
            return new ArrayList<>();
        }
        int resolvedLimit = Math.max(1, limit);
        return extractEntries(resolvedLimit);
    }

    private void validateSaveEntry(int playerId, LeaderboardEntry entry) {
        if (playerId <= 0) {
            throw new IllegalStateException("Cannot save leaderboard entry without a valid user id.");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Leaderboard entry cannot be null.");
        }
    }

    private void saveEntry(Connection connection, int playerId, LeaderboardEntry entry) throws SQLException {
        BestEntry bestEntry = findBestEntry(connection, playerId);
        if (bestEntry == null) {
            insertLeaderboardEntry(connection, playerId, entry);
            return;
        }
        updateIfBetter(connection, bestEntry, entry);
    }

    private void updateIfBetter(Connection connection, BestEntry bestEntry, LeaderboardEntry entry) throws SQLException {
        if (entry.score() > bestEntry.score()) {
            updateLeaderboardEntry(connection, bestEntry.id(), entry);
        }
    }

    private TowerRunStat resolveRunStat(LeaderboardEntry entry) {
        return entry.runStat() != null ? entry.runStat() : new TowerRunStat(0, 0, 0, 0, 0);
    }

    private void bindInsertStatement(PreparedStatement statement, int playerId, int score, TowerRunStat runStat)
            throws SQLException {
        statement.setInt(1, playerId);
        bindScoreAndRunStat(statement, 2, score, runStat);
    }

    private void bindUpdateStatement(PreparedStatement statement, int entryId, int score, TowerRunStat runStat)
            throws SQLException {
        bindScoreAndRunStat(statement, 1, score, runStat);
        statement.setInt(7, entryId);
    }

    private void bindScoreAndRunStat(PreparedStatement statement, int scoreIndex, int score, TowerRunStat runStat)
            throws SQLException {
        statement.setInt(scoreIndex, score);
        bindRunStat(statement, scoreIndex + 1, runStat);
    }

    private void bindRunStat(PreparedStatement statement, int startIndex, TowerRunStat runStat) throws SQLException {
        statement.setInt(startIndex, runStat.floorReached());
        statement.setInt(startIndex + 1, runStat.combatsWon());
        statement.setInt(startIndex + 2, runStat.bugemonsDefeated());
        statement.setInt(startIndex + 3, runStat.bugemonsLost());
        statement.setInt(startIndex + 4, runStat.flawlessFloors());
    }
}
