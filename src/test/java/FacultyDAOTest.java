import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.FacultyDAO;
import custom_exception.*;
import model.Faculty;

class FacultyDAOTest {
    private FacultyDAO facultyDAO;

    @BeforeEach
    void init() {
        facultyDAO = new FacultyDAO();
    }

    @Test
    void testDeleteFacultyByIdThrows() {
        assertThrows(FacultyNotFoundException.class, () -> {
            facultyDAO.deleteFacultyById(-1);
        });
    }

    @Test
    void testGetFacultyByIdOrUserIdNotFound() throws DatabaseConnectionException {
        Faculty faculty = facultyDAO.getFacultyByIdOrUserId(-1);
        assertNull(faculty);
    }
}
