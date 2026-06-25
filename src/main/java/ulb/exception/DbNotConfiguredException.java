package ulb.exception;

/**
 * Thrown when the application cannot connect to the database because mandatory
 * configuration (url/user/password) is missing or invalid.
 *
 * <p>This is treated as an application state/configuration problem rather than
 * a JDBC problem, hence {@link IllegalStateException}.</p>
 */
public class DbNotConfiguredException extends IllegalStateException {
    public DbNotConfiguredException(String message) {
        super(message);
    }

    public DbNotConfiguredException(String message, Throwable cause) {
        super(message, cause);
    }
}
