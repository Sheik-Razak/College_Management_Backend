import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.ExamDAO;
import model.Exam;
import custom_exception.*;

class ExamDAOTest {
    private ExamDAO examDAO;

    @BeforeEach
    void init() {
        examDAO = new ExamDAO();
    }

    @Test
    void testInsertDuplicateExamThrows() throws Exception {
        Exam exam = new Exam();
        exam.setCourseId(2);
        exam.setSubjectId(2);
        exam.setExamDate("2025-11-15");
        exam.setExamType("Assessment");
        exam.setTotalMarks(100);
        examDAO.insertExam(exam);
        assertThrows(DuplicateEntryException.class, () -> {
            examDAO.insertExam(exam);
        });
    }

    @Test
    void testDeleteExamReturnsFalseForMissingId() throws Exception {
        boolean deleted = examDAO.deleteExam(-1);
        assertFalse(deleted);
    }
}
