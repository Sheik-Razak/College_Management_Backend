package custom_exception;

public class UnauthorizedActionException extends Exception {
    public UnauthorizedActionException(String message) {
        super(message);
    }
    public UnauthorizedActionException(String message, Throwable cause) {
        super(message, cause);
    }
}

