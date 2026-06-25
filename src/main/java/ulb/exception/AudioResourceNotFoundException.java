package ulb.exception;

/**
 * Thrown when an expected audio resource is not found on the classpath.
 */
public class AudioResourceNotFoundException extends AudioException {
    public AudioResourceNotFoundException(String message) {
        super(message);
    }

    public AudioResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
