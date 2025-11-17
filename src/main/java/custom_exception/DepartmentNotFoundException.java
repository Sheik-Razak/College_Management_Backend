package custom_exception;

public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

