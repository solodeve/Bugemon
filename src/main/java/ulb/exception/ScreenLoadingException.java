package ulb.exception;

/**
 * Thrown when an FXML screen failed to load or be displayed.
 */
public class ScreenLoadingException extends Exception {

    public ScreenLoadingException(String message) {
        super(message);
    }

    public ScreenLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
