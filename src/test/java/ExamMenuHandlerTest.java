import org.junit.jupiter.api.*;
import java.util.Scanner;
import dao.*;
import model.*;
import java.util.*;

class ExamMenuHandlerTest {
    private TimetableDAO timetableDao;
    private ExamDAO examDao;
    private Faculty faculty;
    private Scanner scanner;
    private ExamMenuHandler handler;

    @BeforeEach
    void setUp() {
        timetableDao = new TimetableDAO();
        examDao = new ExamDAO();
        faculty = new Faculty();
        faculty.setFacultyId(101);
    }

    @Test
    void testAssignExamRealDaoAndScanner() {
        scanner = new Scanner("1\n2025-12-14\nAssessment\n80\n");
        handler = new ExamMenuHandler(timetableDao, examDao, faculty, scanner);
        handler.assignExam();
    }

    @Test
    void testDeleteExamRealDaoAndScanner() {
        scanner = new Scanner("1\n1\n");
        handler = new ExamMenuHandler(timetableDao, examDao, faculty, scanner);
        handler.deleteExam();
    }
}
