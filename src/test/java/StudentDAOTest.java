import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.StudentDAO;
import custom_exception.*;
import model.Student;

class StudentDAOTest {
    private StudentDAO studentDAO;

    @BeforeEach
    void init() {
        studentDAO = new StudentDAO();
    }

    @Test
    void testDeleteStudentByIdThrows() {
        assertThrows(StudentNotFoundException.class, () -> {
            studentDAO.deleteStudentById(-1);
        });
    }

    @Test
    void testGetStudentByUserIdNotFound() throws DatabaseConnectionException {
        Student student = studentDAO.getStudentByUserId(-1);
        assertNull(student);
    }
}
