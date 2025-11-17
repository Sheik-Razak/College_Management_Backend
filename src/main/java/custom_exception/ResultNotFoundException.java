package custom_exception;

public class ResultNotFoundException extends Exception {
    public ResultNotFoundException(String message) {
        super(message);
    }
    public ResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

