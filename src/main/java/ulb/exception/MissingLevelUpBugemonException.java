package ulb.exception;

/**
 * Thrown when no Bugemon is available for the level-up flow.
 */
public class MissingLevelUpBugemonException extends IllegalStateException {
    public MissingLevelUpBugemonException(String message) {
        super(message);
    }
}
