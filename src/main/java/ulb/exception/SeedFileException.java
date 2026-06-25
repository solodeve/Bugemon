package ulb.exception;

/**
  * This exception is thrown whenever the database failed to open a SQL file.
 */
public class SeedFileException extends IllegalStateException {
    public SeedFileException(String message) {
        super(message);
    }

    public SeedFileException(String message,Throwable t) {
        super(message,t);
    }
}
