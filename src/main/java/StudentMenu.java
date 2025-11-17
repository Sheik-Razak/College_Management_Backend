import custom_exception.DatabaseConnectionException;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.AttendanceDAO;
import dao.ResultDAO;
import model.Student;
import dao.TimetableDAO;

import java.util.Scanner;

import custom_exception.StudentNotFoundException;
import custom_exception.TimetableNotFoundException;
import custom_exception.AttendanceNotFoundException;


public class StudentMenu {
    private final Scanner sc = new Scanner(System.in);
    private final int userId;

    public StudentMenu(int userId) {
        this.userId = userId;
    }

    public void showMenu()  {
        StudentDAO studentDao = new StudentDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        AttendanceDAO attendanceDao = new AttendanceDAO();
        ResultDAO resultDao = new ResultDAO();
        TimetableDAO timetableDao = new TimetableDAO();

        Student student = null;
        try {
            student = studentDao.getStudentByUserId(userId);
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        if (student == null) {
            System.out.println(new StudentNotFoundException("Student not found for ID " + userId).getMessage());
            return;
        }

        while (true) {
            System.out.println("\n=== STUDENT DASHBOARD ===");
            System.out.println("1. View Profile");
            System.out.println("2. View Subjects");
            System.out.println("3. View Attendance");
            System.out.println("4. View Exam Results");
            System.out.println("5. View Attendance Percentage");
            System.out.println("6. View TimeTable");
            System.out.println("7. Logout");
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
                        System.out.println(student);
                        break;
                    case 2:
                        System.out.println("-- Your Subjects --");
                        subjectDao.printAllSubjects(student.getCourseId());
                        break;
                    case 3:
                        System.out.println("-- Your Attendance --");
                        try {
                            attendanceDao.printAttendanceByStudentId(student.getStudentId());
                        } catch (AttendanceNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        boolean a = true;
                        while (a) {
                            System.out.println("1. Assessment Result");
                            System.out.println("2. MidTerm Results");
                            System.out.println("3. Final Exam Results");
                            System.out.println("4. Return to Student Menu");
                            int option2;
                            try {
                                option2 = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input, please enter a valid number.");
                                continue;
                            }
                            switch (option2) {
                                case 1:
                                    String type = "Assessment";
                                    resultDao.getResultsByStudentId(student.getStudentId(),type);
                                    break;
                                case 2:
                                    String type2 = "MidTerm";
                                    resultDao.getResultsByStudentId(student.getStudentId(),type2);
                                    break;
                                case 3:
                                    String type3 = "Final Exam";
                                    resultDao.getResultsByStudentId(student.getStudentId(),type3);
                                    break;
                                case 4:
                                    a = false;
                                    break;
                                default:
                                    System.out.println("Invalid option!");
                                    break;
                            }
                        }
                        break;
                    case 5:
                        System.out.println("-- Attendance Percentage --");
                        double percentage = attendanceDao.getAttendancePercentage(student.getStudentId());
                        System.out.println("Your attendance percentage: " + String.format("%.2f", percentage) + "%");
                        break;
                    case 6:
                        try {
                            timetableDao.showDefaultStudentTimetable(student.getStudentId());
                        } catch (TimetableNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
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
