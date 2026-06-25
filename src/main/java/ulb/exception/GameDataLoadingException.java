package ulb.exception;

/**
 * Thrown when game data cannot be loaded during startup (seeding, DB read, etc.).
 */
public class GameDataLoadingException extends Exception {

    /**
     * Constructs a new exception with a detail message and the underlying cause.
     *
     * @param message A human-readable description of what failed to load.
     * @param cause   The I/O or parsing exception that triggered this failure.
     */
    public GameDataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameDataLoadingException(String message) {
        super(message);
    }
}
