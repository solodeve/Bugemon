package ulb.model.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import ulb.configuration.Configuration;
import ulb.exception.DbNotConfiguredException;
import ulb.exception.SeedFileException;

/**
 * PostgreSQL infrastructure helper used to connect to the database and execute SQL seeds.
 *
 * <p>Configuration is resolved from environment variables first
 * ({@code db_url}, {@code db_user}, {@code db_password}), then from
 * {@link Configuration#DATABASE_PROPERTIES_RESOURCE} when variables are absent.</p>
 *
 * <p>Seed resources defined in {@link Configuration#SEED_RESOURCES} are executed
 * at most once per JVM lifecycle through a guarded flag to avoid repeated DDL work.</p>
 */
public class DatabaseBugemon {
    private final String url;
    private final String user;
    private final String password;

    /**
     * Builds a new database helper by loading connection settings.
     */
    public DatabaseBugemon() {
        Properties properties = loadProperties();
        this.url = firstNonBlank(System.getenv("db_url"), properties.getProperty("db_url"));
        this.user = firstNonBlank(System.getenv("db_user"), properties.getProperty("db_user"));
        this.password = firstNonBlank(System.getenv("db_password"), properties.getProperty("db_password"));
    }

    /**
     * Indicates whether all required database connection settings are present.
     *
     * @return {@code true} when URL, user and password are non-blank.
     */
    public boolean isConfigured() {
        return isNonBlank(url) && isNonBlank(user) && isNonBlank(password);
    }

    /**
     * Opens a JDBC connection with auto-commit enabled.
     *
     * @return An active JDBC {@link Connection}.
     * @throws DbNotConfiguredException if mandatory configuration is missing.
     * @throws SQLException if the connection cannot be established.
     */
    public Connection connect() throws DbNotConfiguredException,SQLException {
        if (!isConfigured()) {
            throw new DbNotConfiguredException(Configuration.MISSING_DATABASE_CONFIGURATION_MESSAGE);
        }
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(true);
        return conn;
    }

    /**
     * Returns the configured JDBC URL.
     *
     * @return The database URL, or {@code null} if unresolved.
     */
    public String getUrl() { return url; }

    /**
     * Returns the configured database user.
     *
     * @return The database user, or {@code null} if unresolved.
     */
    public String getUser() { return user; }

    private static volatile boolean seedsExecuted = false;

    /**
     * Executes all configured seed SQL resources once per JVM.
     *
     * <p>If execution fails, the guard is reset so a later retry is possible.</p>
     *
     * @throws SeedFileException if the database is not configured or if a seed fails.
     */
    public void executeSeedFiles() {
        if (!isConfigured()) {
            throw new SeedFileException(
                    "Cannot execute seed files because database configuration is missing.",
                    new DbNotConfiguredException(Configuration.MISSING_DATABASE_CONFIGURATION_MESSAGE)
            );
        }
        if (!markSeedsExecutionStarted()) return;
        executeSeedsSafely();
    }

    /**
     * Runs seed execution in a protected block and wraps SQL failures.
     */
    private void executeSeedsSafely() {
        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            executeSeedResources(statement);
        } catch (SQLException e) {
            markSeedsExecutionFailed();
            throw new SeedFileException("unable to execute seed (SQL) files",e);
        } catch (RuntimeException e) {
            markSeedsExecutionFailed();
            throw e;
        }
    }

    /**
     * Marks the beginning of seed execution.
     *
     * @return {@code true} if this caller should execute seeds, {@code false} if already done.
     */
    private boolean markSeedsExecutionStarted(){
        // Ensure seeds are executed only once per JVM to avoid repeated logging and DDL churn
        synchronized (DatabaseBugemon.class) {
            if (seedsExecuted) {
                return false;
            }
            seedsExecuted = true;
            return true;
        }
    }

    /**
     * Resets the execution guard after a failure.
     */
    private void markSeedsExecutionFailed() {
        synchronized (DatabaseBugemon.class) {
            seedsExecuted = false;
        }
    }

    /**
     * Executes each configured SQL seed resource through the provided statement.
     *
     * @param statement Statement used to run the SQL resources.
     * @throws SQLException if statement execution fails.
     */
    private void executeSeedResources(Statement statement) throws SQLException {
        // Only execute seed resources; do not execute packaged schema.sql which may create extra compatibility tables
        for (String seedResource : Configuration.SEED_RESOURCES) {
            executeSqlResource(statement, seedResource);
        }
    }


    /**
     * Loads database properties from the classpath resource.
     *
     * @return Loaded properties, or an empty set when the resource is absent.
     * @throws IllegalStateException if the resource exists but cannot be read.
     */
    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseBugemon.class.getClassLoader()
                .getResourceAsStream(Configuration.DATABASE_PROPERTIES_RESOURCE)) {
            if (input == null) {
                return props;
            }
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load " + Configuration.DATABASE_PROPERTIES_RESOURCE,e);
        }
    }

    /**
     * Returns the first non-blank value among two candidates.
     *
     * @param first Primary value.
     * @param second Fallback value.
     * @return The first non-blank trimmed value, or {@code null} if none.
     */
    private static String firstNonBlank(String first, String second) {
        if (isNonBlank(first)) {
            return first.trim();
        }
        if (isNonBlank(second)) {
            return second.trim();
        }
        return null;
    }

    /**
     * Checks whether a string is non-null and contains non-whitespace characters.
     *
     * @param value Value to check.
     * @return {@code true} if non-blank.
     */
    private static boolean isNonBlank(String value) {
        return value != null && !value.isBlank();
    }

    /**
     * Reads and executes one SQL resource.
     *
     * @param statement Statement used to execute SQL.
     * @param resourcePath Classpath path to the SQL resource.
     * @throws SQLException if SQL execution fails.
     */
    private void executeSqlResource(Statement statement, String resourcePath) throws SQLException {
        String sql;
        try {
            sql = readResource(resourcePath);
        } catch (IOException | IllegalStateException e){
            throw new SeedFileException("Unable to read SQL resource: " + resourcePath,e);
        }
        if (sql.isBlank()) {
            throw new SeedFileException("SQL resource is empty: " + resourcePath);
        }
        statement.execute(sql);
    }

    /**
     * Reads a classpath resource into a string.
     *
     * @param resourcePath Classpath path to read.
     * @return Full textual content of the resource.
     * @throws IOException if the resource cannot be opened or read.
     */
    private String readResource(String resourcePath) throws IOException {
        try (InputStream input = DatabaseBugemon.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("SQL resource not found: " + resourcePath);
            }
            return new String(input.readAllBytes());
        } catch (IOException e) {
            throw new IOException("Unable to read SQL resource: " + resourcePath,e);
        }
    }
}
