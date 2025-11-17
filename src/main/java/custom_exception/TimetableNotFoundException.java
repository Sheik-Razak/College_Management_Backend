package custom_exception;

public class TimetableNotFoundException extends Exception {
    public TimetableNotFoundException(String message) {
        super(message);
    }
    public TimetableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
