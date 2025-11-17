package custom_exception;

public class GradeCalculationException extends Exception {
    public GradeCalculationException(String message) {
        super(message);
    }
    public GradeCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}

