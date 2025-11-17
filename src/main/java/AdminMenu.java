import custom_exception.*;
import dao.AdminDAO;
import dao.AttendanceDAO;
import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.FacultyDAO;
import dao.ResultDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import model.Department;

import java.sql.*;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc = new Scanner(System.in);

    public void showMenu() throws SubjectNotFoundException, AttendanceNotFoundException, DatabaseConnectionException {
        UserDAO userDao = new UserDAO();
        StudentDAO studentDao = new StudentDAO();
        FacultyDAO facultyDao = new FacultyDAO();
        AdminDAO adminDao = new AdminDAO();
        DepartmentDAO deptDao = new DepartmentDAO();
        AttendanceDAO attendanceDao = new AttendanceDAO();
        ResultDAO resultDao = new ResultDAO();
        CourseDAO courseDao = new CourseDAO();
        SubjectDAO subjectDao = new SubjectDAO();

        while (true) {
            System.out.println("\n=== ADMIN PANEL ===");
            System.out.println("1. View All Users");
            System.out.println("2. Approve/Reject User Signup Requests");
            System.out.println("3. View Results");
            System.out.println("4. Manage Departments");
            System.out.println("5. Delete Student/Faculty");
            System.out.println("6. View All Attendance Records");
            System.out.println("7. View All Courses");
            System.out.println("8. Manage Subjects");
            System.out.println("9. Logout");
            System.out.print("Choose option: ");
            int option;
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number.");
                continue;
            }
            switch (option) {
                case 1:
                    boolean a = true;
                    while (a) {
                        System.out.println("1. All Users");
                        System.out.println("2. All Students");
                        System.out.println("3. All Faculty");
                        System.out.println("4. All Admins");
                        System.out.println("5. Return to Admin menu");
                        int option1;
                        try {
                            option1 = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please enter a valid number.");
                            continue;
                        }
                        switch (option1) {
                            case 1:
                                try {
                                    userDao.printAllUsers();
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error fetching users: " + e.getMessage());
                                }
                                break;
                            case 2:
                                try {
                                    studentDao.printAllStudents();
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error fetching students: " + e.getMessage());
                                }
                                break;
                            case 3:
                                try {
                                    facultyDao.printAllFaculty();
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error fetching faculty: " + e.getMessage());
                                }
                                break;
                            case 4:
                                adminDao.printAllAdmins();
                                break;
                            case 5:
                                a = false;
                                break;
                            default:
                                System.out.println("Invalid option");
                                break;
                        }
                    }
                    break;
                case 2:
                    handlePendingUserRequests();
                    break;
                case 3:
                    boolean b = true;
                    while (b) {
                        System.out.println("1. Assessment Results");
                        System.out.println("2. Mid-term Results");
                        System.out.println("3. Final Examination Results");
                        System.out.println("4. Return to Admin menu");
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
                case 4:
                    boolean c = true;
                    while (c) {
                        System.out.println("\n--- Department Management ---");
                        System.out.println("1. View all departments");
                        System.out.println("2. Add new department");
                        System.out.println("3. Delete department");
                        System.out.println("4. Back to main menu");
                        System.out.print("Choose option: ");
                        int deptOp;
                        try {
                            deptOp = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please enter a valid number.");
                            continue;
                        }
                        switch (deptOp) {
                            case 1:
                                try {
                                    deptDao.printAllDepartments();
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error fetching departments: " + e.getMessage());
                                }
                                break;
                            case 2:
                                System.out.print("Department Name: ");
                                String name = sc.nextLine();
                                System.out.print("Department Head: ");
                                String head = sc.nextLine();
                                System.out.print("Office Location: ");
                                String loc = sc.nextLine();
                                try {
                                    boolean added = deptDao.insertDepartment(new Department(0, name, head, loc));
                                    if (!added) throw new DuplicateEntryException("Department name already exists.");
                                    System.out.println("Department added.");
                                } catch (DuplicateEntryException e) {
                                    System.out.println(e.getMessage());
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error adding department: " + e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.print("Enter Department ID: ");
                                    int deptId = Integer.parseInt(sc.nextLine());
                                try {
                                    boolean deleted = deptDao.deleteDepartment(deptId);
                                    if (!deleted) throw new DepartmentNotFoundException("Department not found for ID: " + deptId);
                                    System.out.println("Department deleted.");
                                } catch (DepartmentNotFoundException e) {
                                    System.out.println(e.getMessage());
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error deleting department: " + e.getMessage());
                                }
                                break;
                            case 4:
                                c = false;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    }
                    break;
                case 5:
                    boolean d = true;
                    while (d) {
                        System.out.println("1. Delete a Student");
                        System.out.println("2. Delete a Faculty");
                        System.out.println("3. Return to Admin menu");
                        int option3;
                        try {
                            option3 = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please enter a valid number.");
                            continue;
                        }
                        switch (option3) {
                            case 1:
                                System.out.print("Enter student ID to delete: ");
                                int studId = Integer.parseInt(sc.nextLine());
                                try {
                                    boolean studDeleted = studentDao.deleteStudentById(studId);
                                    if (!studDeleted) throw new StudentNotFoundException("Student not found for ID: " + studId);
                                    System.out.println("Student deleted.");
                                } catch (StudentNotFoundException e) {
                                    System.out.println(e.getMessage());
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error deleting student: " + e.getMessage());
                                }
                                break;
                            case 2:
                                System.out.print("Enter faculty ID to delete: ");
                                int facId = Integer.parseInt(sc.nextLine());
                                try {
                                    boolean facDeleted = facultyDao.deleteFacultyById(facId);
                                    if (!facDeleted) throw new FacultyNotFoundException("Faculty not found for ID: " + facId);
                                    System.out.println("Faculty deleted.");
                                } catch (FacultyNotFoundException e) {
                                    System.out.println(e.getMessage());
                                } catch (DatabaseConnectionException e) {
                                    System.out.println("Database error deleting faculty: " + e.getMessage());
                                }
                                break;
                            case 3:
                                d = false;
                                break;
                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                    }
                    break;
                case 6:
                    attendanceDao.printAllAttendance();
                    break;
                case 7:
                    try {
                        courseDao.printAllCourses();
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error fetching course list: " + e.getMessage());
                    }
                    break;
                case 8:
                    boolean b1 = true;
                    while (b1) {
                        System.out.println("1. View All Subjects");
                        System.out.println("2. Insert and Assign Subject");
                        System.out.println("3. Delete Subject");
                        System.out.println("4. Back to Admin menu");
                        int option2;
                        try {
                            option2 = Integer.parseInt(sc.nextLine());
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid input, please enter a valid number.");
                            continue;
                        }
                        switch (option2) {
                            case 1:
                                subjectDao.getAllSubjects();
                                break;
                            case 2:
                                System.out.print("Enter Subject Name: ");
                                String subName = sc.nextLine();
                                System.out.print("Enter Course ID: ");
                                int courseId = sc.nextInt();
                                System.out.print("Enter Faculty ID: ");
                                int facultyId = sc.nextInt();
                                System.out.print("Enter Semester No: ");
                                int semester = sc.nextInt();
                                System.out.print("Enter Credits: ");
                                int credits = sc.nextInt();
                                sc.nextLine();
                                try {
                                    boolean added = subjectDao.insertSubject(subName, courseId, facultyId, semester, credits);
                                    if (!added) throw new DuplicateEntryException("Subject name already exists or faculty ID invalid.");
                                    System.out.println("Subject added and assigned.");
                                } catch (DuplicateEntryException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.print("Enter Subject ID to Delete: ");
                                int subjectId = sc.nextInt();
                                sc.nextLine();
                                try {
                                    boolean deleted = subjectDao.deleteSubject(subjectId);
                                    if (!deleted) throw new SubjectNotFoundException("Subject not found for ID: " + subjectId);
                                    System.out.println("Subject deleted.");
                                } catch (SubjectNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 4:
                                b1 = false;
                                break;
                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                    }
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void handlePendingUserRequests() {
        System.out.println("\nPENDING USER REQUESTS:");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-8s %-10s %-12s %-15s %-30s %-30s %-8s %-15s %-12s %-25s %-10s %-15s %-8s%n",
                "ID", "Full Name", "Gender", "Type", "DOB", "Phone", "Email", "Address", "Course", "Qualif.", "Dept.", "Submission", "Status", "Username", "AdmYear");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        try (Connection con = dao.DBUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pending_user WHERE status = 'Pending'");
            boolean found = false;
            while (rs.next()) {
                int pendingId = rs.getInt("pending_id");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String userType = rs.getString("user_type");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int courseId = rs.getInt("course_id");
                String qualification = rs.getString("qualification");
                int deptId = rs.getInt("department_id");
                Timestamp submissionDate = rs.getTimestamp("submission_date");
                String status = rs.getString("status");
                String username = rs.getString("username");
                int admissionYear = rs.getInt("admissionYear");
                System.out.printf("%-5d %-20s %-8s %-10s %-12s %-15s %-30s %-30s %-8d %-15s %-12d %-25s %-10s %-15s %-8d%n",
                        pendingId, fullName, gender, userType,
                        dob != null ? dob.toString() : "",
                        phone, email, address, courseId, qualification, deptId,
                        submissionDate != null ? submissionDate.toString() : "",
                        status, username, admissionYear);
                found = true;
                System.out.print("Action for this user (APPROVE/REJECT/SKIP): ");
                String action = sc.nextLine().trim().toUpperCase();
                try {
                    if ("APPROVE".equals(action)) {
                        boolean ok = approvePendingUser(username, con);
                        System.out.println(ok ? "User approved and added." : "Approval failed.");
                    } else if ("REJECT".equals(action)) {
                        rejectPendingUser(username, con);
                        System.out.println("User rejected and removed from pending.");
                    } else {
                        System.out.println("Skipped this request.");
                    }
                } catch (UserNotFoundException e) {
                    System.out.println(" " + e.getMessage());
                } catch (DuplicateEntryException e) {
                    System.out.println(" " + e.getMessage());
                } catch (DatabaseConnectionException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }
            if (!found) {
                System.out.println("No pending requests found.");
            }
            rs.close();
            stmt.close();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private boolean approvePendingUser(String username, Connection con)
            throws UserNotFoundException, DuplicateEntryException, DatabaseConnectionException {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pending_user WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close(); ps.close();
                throw new UserNotFoundException("No such pending user found: " + username);
            }
            PreparedStatement userSt = con.prepareStatement(
                    "INSERT INTO user (username, email, password, user_type, status) VALUES (?, ?, ?, ?, 'Active')",
                    Statement.RETURN_GENERATED_KEYS
            );
            userSt.setString(1, rs.getString("username"));
            userSt.setString(2, rs.getString("email"));
            userSt.setString(3, rs.getString("password"));
            userSt.setString(4, rs.getString("user_type"));
            try {
                userSt.executeUpdate();
            } catch (SQLException e) {
                userSt.close();
                throw new DuplicateEntryException("User with this username or email may already exist!");
            }
            ResultSet keys = userSt.getGeneratedKeys();
            int userId = -1;
            if (keys.next())
                userId = keys.getInt(1);
            keys.close();
            userSt.close();

            if ("Student".equalsIgnoreCase(rs.getString("user_type"))) {
                PreparedStatement studSt = con.prepareStatement(
                        "INSERT INTO student (user_id, full_name, gender, dob, course_id, phone, email, address, admission_year) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, YEAR(CURRENT_DATE))"
                );
                studSt.setInt(1, userId);
                studSt.setString(2, rs.getString("full_name"));
                studSt.setString(3, rs.getString("gender"));
                studSt.setDate(4, rs.getDate("dob"));
                studSt.setInt(5, rs.getInt("course_id"));
                studSt.setString(6, rs.getString("phone"));
                studSt.setString(7, rs.getString("email"));
                studSt.setString(8, rs.getString("address"));
                studSt.executeUpdate();
                studSt.close();
            } else if ("Faculty".equalsIgnoreCase(rs.getString("user_type"))) {
                PreparedStatement facSt = con.prepareStatement(
                        "INSERT INTO faculty (user_id, full_name, gender, dob, qualification, department_id, phone, email, address) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );
                facSt.setInt(1, userId);
                facSt.setString(2, rs.getString("full_name"));
                facSt.setString(3, rs.getString("gender"));
                facSt.setDate(4, rs.getDate("dob"));
                facSt.setString(5, rs.getString("qualification"));
                facSt.setInt(6, rs.getInt("department_id"));
                facSt.setString(7, rs.getString("phone"));
                facSt.setString(8, rs.getString("email"));
                facSt.setString(9, rs.getString("address"));
                facSt.executeUpdate();
                facSt.close();
            }

            PreparedStatement delPend = con.prepareStatement(
                    "DELETE FROM pending_user WHERE username = ?"
            );
            delPend.setString(1, username);
            delPend.executeUpdate();
            delPend.close();
            rs.close();
            ps.close();
            return true;
        } catch (UserNotFoundException | DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error during approval: " + e.getMessage());
        }
    }

    private void rejectPendingUser(String username, Connection con) throws DatabaseConnectionException {
        try {
            PreparedStatement delPend = con.prepareStatement(
                    "DELETE FROM pending_user WHERE username = ?"
            );
            delPend.setString(1, username);
            delPend.executeUpdate();
            delPend.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error during rejection: " + e.getMessage());
        }
    }
}
