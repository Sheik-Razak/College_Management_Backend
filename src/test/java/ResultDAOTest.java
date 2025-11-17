import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.ResultDAO;
import custom_exception.*;

class ResultDAOTest {
    private ResultDAO resultDAO;

    @BeforeEach
    void init() {
        resultDAO = new ResultDAO();
    }

    @Test
    void testInsertDuplicateAssessmentResultThrows() throws Exception {
        int studentId = 1;
        int subjectId = 1;
        int marks = 18;
        String grade = "A";
        assertTrue(resultDAO.insertAssessmentResult(studentId, subjectId, marks, grade));
                assertThrows(DuplicateEntryException.class, () -> {
            resultDAO.insertAssessmentResult(studentId, subjectId, marks, grade);
        });
    }

    @Test
    void testInsertAssessmentNoExamReturnsFalse() throws Exception {
               assertFalse(resultDAO.insertAssessmentResult(-1, -1, 10, "B"));
    }
}
