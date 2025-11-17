import custom_exception.*;
import dao.FacultyDAO;
import dao.SubjectDAO;
import dao.TimetableDAO;
import dao.AttendanceDAO;
import model.Exam;
import model.Faculty;
import model.Subject;
import model.Timetable;
import java.util.List;
import java.util.Scanner;
import dao.ResultDAO;
import dao.ExamDAO;
import dao.ExamMenuHandler;


public class FacultyMenu {
    private final Scanner sc = new Scanner(System.in);
    private final int userId;

    public FacultyMenu(int userId) {
        this.userId = userId;
    }

    public void showMenu() throws DatabaseConnectionException {
        FacultyDAO facultyDao = new FacultyDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        TimetableDAO timetableDao = new TimetableDAO();
        AttendanceDAO attendanceDao = new AttendanceDAO();
        ResultDAO resultDao = new ResultDAO();
        ExamDAO examDao = new ExamDAO();

        Faculty faculty = facultyDao.getFacultyByIdOrUserId(userId);
        if (faculty == null) {
            System.out.println("Faculty record not found!");
            return;
        }
        ExamMenuHandler examHandler = new ExamMenuHandler(timetableDao, examDao, faculty, sc);

        while (true) {
            System.out.println("\n=== FACULTY DASHBOARD ===");
            System.out.println("1. View Profile");
            System.out.println("2. View Subjects Assigned");
            System.out.println("3. View Timetable");
            System.out.println("4. Mark Attendance for a Student");
            System.out.println("5. Manage Exams");
            System.out.println("6. View Results");
            System.out.println("7. Assign Marks");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");
            int option;
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number.");
                continue;
            }

            try {
                switch (option) {
                    case 1:
                        System.out.println("-- Your Profile --");
                        System.out.println(faculty);
                        break;
                    case 2:
                        System.out.println("-- Subjects Assigned --");
                        try {
                            subjectDao.printSubjectsByFacultyId(faculty.getFacultyId());
                        } catch (SubjectNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("-- Your Timetable --");
                        try {
                            timetableDao.printTimetableByFacultyId(faculty.getFacultyId());
                        } catch (TimetableNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Enter subject ID: ");
                        int subjectId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Date(YYYY-MM-DD): ");
                        String date = sc.nextLine();
                        Timetable timetable;
                        try {
                            timetable = timetableDao.getTimetableById(subjectId);
                            if (timetable == null) throw new TimetableNotFoundException("No class for this subject on this date!");
                            String start = timetable.getStartTime();
                            String end = timetable.getEndTime();
                            attendanceDao.markAttendance(subjectId, date, start, end);
                            System.out.println("Attendance marked.");
                        } catch (TimetableNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        boolean a = true;
                        while (a) {
                            System.out.println("1. Assign Exam");
                            System.out.println("2. Delete Exam");
                            System.out.println("3. All Exams");
                            System.out.println("4. Return Back to Faculty Menu");
                            int option1;
                            try {
                                option1 = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input, please enter a valid number.");
                                continue;
                            }
                            switch (option1) {
                                case 1:
                                    examHandler.assignExam();
                                    break;
                                case 2:
                                    examHandler.deleteExam();
                                    break;
                                case 3:
                                    examDao.getAllExams();
                                    break;
                                case 4:
                                    a = false;
                                    break;
                                default:
                                    System.out.println("Invalid input, please enter a valid number.");
                                    break;
                            }
                        }
                        break;
                    case 6:
                        boolean b = true;
                        while (b) {
                            System.out.println("1. Assessment Results");
                            System.out.println("2. Mid-term Results");
                            System.out.println("3. Final Examination Results");
                            System.out.println("4. Return to Faculty menu");
                            int option2;
                            try {
                                option2 = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input, please enter a valid number.");
                                continue;
                            }
                            switch (option2) {
                                case 1:
                                    resultDao.assessmentResults();
                                    break;
                                case 2:
                                    resultDao.midtermResults();
                                    break;
                                case 3:
                                    resultDao.finalExamResults();
                                    break;
                                case 4:
                                    b = false;
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    break;
                            }
                        }
                        break;
                    case 7:
                        boolean c = true;
                        while (c) {
                            System.out.println("1. Assessment Marks");
                            System.out.println("2. MidTerm Marks");
                            System.out.println("3. Final Examination Marks");
                            System.out.println("4. Return to Faculty menu");
                            int option3;
                            try {
                                option3 = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input, please enter a valid number.");
                                continue;
                            }
                            switch (option3) {
                                case 1:
                                    resultDao.enterAssessmentMarksForSubject();
                                    break;
                                case 2:
                                    resultDao.enterMidtermMarksForSubject();
                                    break;
                                case 3:
                                    resultDao.enterFinalMarksForSubject();
                                    break;
                                case 4:
                                    c = false;
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    break;
                            }
                        }
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}


