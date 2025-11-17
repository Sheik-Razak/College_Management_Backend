import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.AttendanceDAO;
import model.Attendance;
import custom_exception.*;

class AttendanceDAOTest {
    private AttendanceDAO attendanceDAO;

    @BeforeEach
    void init() {
        attendanceDAO = new AttendanceDAO();
    }

    @Test
    void testInsertDuplicateAttendanceThrows() throws Exception {
        Attendance a = new Attendance(9, 2, 2, "2025-12-12", "Present");
        assertTrue(attendanceDAO.insertAttendance(a));
        assertThrows(DuplicateEntryException.class, () -> {
            attendanceDAO.insertAttendance(a);
        });
    }

    @Test
    void testGetAttendancePercentageZero() {
        double percent = attendanceDAO.getAttendancePercentage(-1);
        assertEquals(0.0, percent, 0.00001);
    }
}
