package ulb.exception;

/**
 * Thrown when audio decoding or playback fails.
 */
public class AudioPlaybackException extends AudioException {
    public AudioPlaybackException(String message, Throwable cause) {
        super(message, cause);
    }
}
