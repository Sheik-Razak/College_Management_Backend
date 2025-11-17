package custom_exception;

public class FacultyNotFoundException extends Exception {
    public FacultyNotFoundException(String message) {
        super(message);
    }
    public FacultyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

