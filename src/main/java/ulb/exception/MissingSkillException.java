package ulb.exception;

/**
 * Thrown when a skill is missing.
 * It's typically used in case the different attack of a bugemon are not displayed during battle.
 */
public class MissingSkillException extends RuntimeException {
    public MissingSkillException(String message) {
        super(message);
    }

    public MissingSkillException(String message, Throwable cause) {
        super(message, cause);
    }
}
