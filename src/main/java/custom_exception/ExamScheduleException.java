package custom_exception;

public class ExamScheduleException extends Exception {
    public ExamScheduleException(String message) {
        super(message);
    }
    public ExamScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}

