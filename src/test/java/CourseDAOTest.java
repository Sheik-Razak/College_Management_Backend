import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.CourseDAO;

class CourseDAOTest {
    private CourseDAO courseDAO;

    @BeforeEach
    void init() {
        courseDAO = new CourseDAO();
    }

    @Test
    void testPrintAllCoursesDoesNotThrow() {
        assertDoesNotThrow(() -> {
            courseDAO.printAllCourses();
        });
    }
}
