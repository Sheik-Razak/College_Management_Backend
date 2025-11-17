import static org.junit.jupiter.api.Assertions.*;

import custom_exception.DatabaseConnectionException;
import org.junit.jupiter.api.*;
import dao.TimetableDAO;
import model.Timetable;
import custom_exception.TimetableNotFoundException;
import java.util.List;

class TimetableDAOTest {
    private TimetableDAO dao;

    @BeforeEach
    void setUp() {
        dao = new TimetableDAO();
    }

    @Test
    void testInsertAndGetTimetable() throws Exception {
        Timetable tt = new Timetable();
        tt.setCourseId(1);
        tt.setSubjectId(1);
        tt.setFacultyId(1);
        tt.setDayOfWeek("Monday");
        tt.setStartTime("10:00");
        tt.setEndTime("11:00");
        tt.setRoomNo("C-101");
        assertTrue(dao.insertTimetable(tt));
        Timetable fetched = dao.getTimetableById(1);
        assertNotNull(fetched);
        assertEquals("Monday", fetched.getDayOfWeek());
    }

    @Test
    void testGetTimetableByIdNotFoundThrows() {
        assertThrows(TimetableNotFoundException.class, () -> {
            dao.getTimetableById(-1);
        });
    }

    @Test
    void testGetSubjectsForFacultyInTimetableReturnsList() throws DatabaseConnectionException {
        List<?> subjects = dao.getSubjectsForFacultyInTimetable(1);
        assertNotNull(subjects);
    }
}
