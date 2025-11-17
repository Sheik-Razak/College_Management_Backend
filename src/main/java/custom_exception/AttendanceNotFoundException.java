package custom_exception;

public class AttendanceNotFoundException extends Exception {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
    public AttendanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
