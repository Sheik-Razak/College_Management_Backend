import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.SubjectDAO;
import custom_exception.*;

class SubjectDAOTest {
    private SubjectDAO subjectDAO;

    @BeforeEach
    void init() {
        subjectDAO = new SubjectDAO();
    }

    @Test
    void testInsertDuplicateSubjectThrows() throws Exception {
        subjectDAO.insertSubject("Maths", 1, 1, 1, 4);
        assertThrows(DuplicateEntryException.class, () -> {
            subjectDAO.insertSubject("Maths", 1, 1, 1, 4);
        });
    }

    @Test
    void testDeleteSubjectThrows() {
        assertThrows(SubjectNotFoundException.class, () -> {
            subjectDAO.deleteSubject(-1);
        });
    }

    @Test
    void testPrintSubjectsByFacultyIdNoSubjects() {
        assertThrows(SubjectNotFoundException.class, () -> {
            subjectDAO.printSubjectsByFacultyId(-1);
        });
    }

    @Test
    void testPrintAllSubjectsNoSubjects() {
        assertThrows(SubjectNotFoundException.class, () -> {
            subjectDAO.printAllSubjects(-1);
        });
    }
}
