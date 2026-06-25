package ulb.model.infrastructure;

import ulb.exception.DbNotConfiguredException;
import ulb.model.user.User;
import ulb.configuration.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data access object for {@link User} entities.
 *
 * <p>This DAO centralizes all user-related SQL operations (lookup, authentication
 * and creation) through prepared statements defined in {@link SQLQueries}.
 * It relies on {@link DatabaseBugemon} for connection management and for
 * one-time execution of seed resources.</p>
 */
public class UserDao {
    /**
     * Database gateway used to open JDBC connections and initialize SQL resources.
     */
    private final PasswordHasher passwordHasher;
    private final DatabaseBugemon databaseBugemon;

    /**
     * Creates a new DAO bound to the given database gateway.
     *
     * @param databaseBugemon Database helper used by this DAO. Must not be {@code null}.
     */
    public UserDao(DatabaseBugemon databaseBugemon) {
        this.databaseBugemon = databaseBugemon;
        this.passwordHasher = new PasswordHasher();
    }

    /**
     * Ensures required SQL resources (tables and seed files) are available.
     *
     * @throws DbNotConfiguredException if database configuration is missing.
     */
    public void ensureTableExists() throws DbNotConfiguredException {
        databaseBugemon.executeSeedFiles();
    }

    /**
     * Finds a user by its identifier.
     *
     * @param searchedId The user id to search.
     * @return An {@link Optional} containing the user if found, otherwise empty.
     * @throws IllegalStateException if the query cannot be executed.
     */
    public Optional<User> findById(int searchedId) {
        try (Connection connection = databaseBugemon.connect()) {
            ensureTableExists();
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_FIND_BY_ID)) {
                statement.setInt(1, searchedId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load user by id.", e);
        }
    }

    /**
     * Finds a user by username.
     *
     * @param searchedUsername The username to search.
     * @return An {@link Optional} containing the user if found, otherwise empty.
     * @throws IllegalStateException if the query cannot be executed.
     */
    public Optional<User> findByUsername(String searchedUsername) {
        try (Connection connection = databaseBugemon.connect()) {
            ensureTableExists();
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_FIND_BY_USERNAME)) {
                statement.setString(1, searchedUsername);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load user by username.", e);
        }
    }

    /**
     * Authenticates a user with a username/password pair.
     *
     * @param searchedUsername The username to authenticate.
     * @param rawPassword The plain-text password provided by the caller.
     * @return An {@link Optional} containing the authenticated user if credentials match.
     * @throws IllegalStateException if the query cannot be executed.
     */
    public Optional<User> authenticate(String searchedUsername, String rawPassword) {
        try (Connection connection = databaseBugemon.connect()) {
            ensureTableExists();
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_FIND_BY_USERNAME_WITH_PASSWORD)) {
                statement.setString(1, searchedUsername);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {return Optional.empty();}
                    String storedHash = resultSet.getString("password_hash");
                    if (!passwordHasher.matches(rawPassword, storedHash)) {
                        return Optional.empty();
                    }
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to authenticate user.", e);
        }
    }

    /**
     * Creates and returns a new user.
     *
     * @param newUsername The username of the new user.
     * @param rawPassword The plain-text password to persist.
     * @return The created {@link User}.
     * @throws IllegalStateException if user creation fails.
     */
    public User create(String newUsername, String rawPassword) {
        try (Connection connection = databaseBugemon.connect()) {
            ensureTableExists();
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_INSERT_RETURNING)) {
                statement.setString(1, newUsername);
                statement.setString(2, passwordHasher.hash(rawPassword));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {throw new SQLException("No user returned by INSERT statement.");}
                    return mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to create user.", e);
        }
    }

    /**
     * Maps the current row of a result set to a {@link User}.
     *
     * @param resultSet JDBC result set positioned on a valid row.
     * @return The mapped user instance.
     * @throws SQLException if required columns cannot be read.
     */
    private static User mapRow(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
