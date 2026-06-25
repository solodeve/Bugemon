package ulb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

import ulb.configuration.SQLQueries;
import ulb.model.infrastructure.DatabaseBugemon;

/**
 * Manages the set of Bugemons unlocked by the current user.
 *
 * <p>Call {@link #loadForUser} after authentication and {@link #clear} on logout.</p>
 */
public class UnlockRepository implements IUnlockRepository {

    private final Set<String> unlockedBugemonIds = new LinkedHashSet<>();
    private final DatabaseBugemon databaseBugemon;
    private Integer userId;

    public UnlockRepository(DatabaseBugemon databaseBugemon) {
        this.databaseBugemon = databaseBugemon;
    }

    public void clear() {
        unlockedBugemonIds.clear();
        userId = null;
    }

    /**
     * Loads the unlocked Bugemons for the given user, restricted to {@code knownBugemonIds}.
     * Call after authentication; pairs with {@link #clear()} on logout.
     */
    public void loadForUser(Integer userId, Set<String> knownBugemonIds) {
        this.userId = userId;
        unlockedBugemonIds.clear();
        if (isDatabaseAvailable()) loadFromDatabase(knownBugemonIds);
    }

    /** Returns the live set of unlocked IDs (shared reference — modifications are reflected immediately). */
    Set<String> getUnlockedBugemonIds() { return unlockedBugemonIds; }

    @Override
    public boolean isBugemonUnlocked(String bugemonId) {
        return bugemonId != null && unlockedBugemonIds.contains(bugemonId);
    }

    /**
     * Unlocks the given Bugemon. Returns {@code false} if already unlocked or the database write fails.
     */
    @Override
    public boolean unlockBugemon(String bugemonId) {
        if (unlockedBugemonIds.contains(bugemonId)) return false;
        if (persistToDatabase(bugemonId)) {
            unlockedBugemonIds.add(bugemonId);
            return true;
        }
        return false;
    }

    private boolean isDatabaseAvailable() {
        return databaseBugemon.isConfigured() && userId != null && userId > 0;
    }

    private void loadFromDatabase(Set<String> knownBugemonIds) {
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USER_UNLOCKED_BUGEMONS)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("bugemon_id");
                    if (knownBugemonIds.contains(id)) unlockedBugemonIds.add(id);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UnlockRepository.class.getName())
                    .log(Level.WARNING, "Failed to load unlocks, starting with none.", e);
        }
    }

    private boolean persistToDatabase(String bugemonId) {
        if (!isDatabaseAvailable()) return false;
        try (Connection conn = databaseBugemon.connect();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.INSERT_USER_UNLOCKED_BUGEMON)) {
            stmt.setInt(1, userId);
            stmt.setString(2, bugemonId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
