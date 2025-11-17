package custom_exception;

public class AttendanceNotMarkedException extends Exception {
    public AttendanceNotMarkedException(String message) {
        super(message);
    }
    public AttendanceNotMarkedException(String message, Throwable cause) {
        super(message, cause);
    }
}

