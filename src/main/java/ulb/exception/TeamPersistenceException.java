package ulb.exception;

/**
 * Thrown when team data cannot be loaded from or saved to persistent storage.
 *
 * <p>Used by team persistence implementations when reading or writing stored
 * teams fails due to I/O errors or parsing problems.</p>
 */
public class TeamPersistenceException extends Exception {

    /**
     * Constructs a new exception with a detail message and the underlying cause.
     *
     * @param message A human-readable description of the persistence failure.
     * @param cause   The I/O or parsing exception that triggered this failure.
     */
    public TeamPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with only a detail message.
     * Use this constructor when there is no underlying exception to wrap.
     *
     * @param message A human-readable description of the persistence failure.
     */
    public TeamPersistenceException(String message) {
        super(message);
    }
}
