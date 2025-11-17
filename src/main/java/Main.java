import custom_exception.*;
import dao.UserDAO;
import model.User;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDao = new UserDAO();

        while (true) {
            System.out.println("\n=== COLLEGE MANAGEMENT SYSTEM ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Faculty Login");
            System.out.println("3. Student Login");
            System.out.println("4. Signup");
            System.out.println("5. Exit");
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
                    System.out.print("Admin Username: ");
                    String adminUser = sc.nextLine();
                    System.out.print("Password: ");
                    String adminPass = sc.nextLine();
                    try {
                        if (userDao.validateLogin(adminUser, adminPass, "Admin")) {
                            System.out.println("Admin Login Successful!");
                            new AdminMenu().showMenu();
                        } else {
                            throw new UserNotFoundException("Invalid admin login.");
                        }
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error: " + e.getMessage());
                    } catch (SubjectNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (AttendanceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    System.out.print("Faculty Username: ");
                    String facUser = sc.nextLine();
                    System.out.print("Password: ");
                    String facPass = sc.nextLine();
                    try {
                        if (userDao.validateLogin(facUser, facPass, "Faculty")) {
                            System.out.println("Faculty Login Successful!");
                            User facUserObj = userDao.getUserByUsername(facUser);
                            new FacultyMenu(facUserObj.getUserId()).showMenu();
                        } else {
                            throw new UserNotFoundException("Invalid faculty login.");
                        }
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Student Username: ");
                    String studUser = sc.nextLine();
                    System.out.print("Password: ");
                    String studPass = sc.nextLine();
                    try {
                        if (userDao.validateLogin(studUser, studPass, "Student")) {
                            System.out.println("Student Login Successful!");
                            User u = userDao.getUserByUsername(studUser);
                            new StudentMenu(u.getUserId()).showMenu();
                        } else {
                            throw new UserNotFoundException("Invalid student login.");
                        }
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("=== SIGNUP ===");
                    System.out.print("Choose user type (Faculty/Student): ");
                    String type = sc.nextLine().trim().toLowerCase();
                    try {
                        if(type.equals("faculty")){
                            System.out.print("Full Name: ");
                            String fullName = sc.nextLine();
                            System.out.print("Gender (Male/Female/Other): ");
                            String gender = sc.nextLine();
                            System.out.print("Date of Birth (yyyy-MM-dd): ");
                            String dob = sc.nextLine();
                            System.out.print("Qualification: ");
                            String qualification = sc.nextLine();
                            System.out.print("Department ID:\n" +
                                    "1. Department of Civil Engineering\n" +
                                    "2. Dept. of Electrical & Electronics\n" +
                                    "3. Department of Mechanical Engineering\n" +
                                    "4. Dept. of Electronics & Communication\n" +
                                    "5. Dept. of Computer Science & Engineering\n" +
                                    "6. Department of Information Technology\n" +
                                    "7. Department of Chemical Engineering\n" +
                                    "8. Department of Biotechnology\n" +
                                    "9. Dept. of Aeronautical Engineering\n" +
                                    "10. Department of Automobile Engineering\n ");
                            System.out.print("Enter Your Choice: ");
                            int departmentId = sc.nextInt();
                            sc.nextLine();
                            String phone;
                            while (true) {
                                System.out.print("Phone: ");
                                phone = sc.nextLine().trim();
                                int len = phone.length();
                                if (len >= 10 && len <= 12) {
                                    break;
                                } else {
                                    System.out.println("Phone number is less than 10 digits or more than 12 digits. Try again.");
                                }
                            }
                            String email;
                            while (true) {
                                System.out.print("Email: ");
                                email = sc.nextLine();
                                if (email.toLowerCase().endsWith(".com")) {
                                    break;
                                } else {
                                    System.out.println("Email address is not valid. Please Enter Valid Email.");
                                }
                            }
                            System.out.print("Address: ");
                            String address = sc.nextLine();
                            System.out.print("Username: ");
                            String username = sc.nextLine();
                            System.out.print("Password: ");
                            String password = sc.nextLine();
                            if (userDao.signup(type, username, email, password, fullName, gender, dob, phone, address, qualification, departmentId)) {
                                System.out.println("Signup successful! Please wait for approval.");
                            } else {
                                throw new DuplicateEntryException("Signup failed (username or email may already exist).");
                            }
                        }
                        else if(type.equals("student")){
                            System.out.print("Full Name: ");
                            String fullName = sc.nextLine();
                            System.out.print("Gender (Male/Female/Other): ");
                            String gender = sc.nextLine();
                            System.out.print("Date of Birth (yyyy-MM-dd): ");
                            String dob = sc.nextLine();
                            System.out.print("Course ID:\n" +
                                    "1. Civil Engineering\n" +
                                    "2. EEE\n" +
                                    "3. Mechanical Engineering\n" +
                                    "4. ECE\n" +
                                    "5. CSE\n" +
                                    "6. Information Technology\n" +
                                    "7. Chemical Engineering\n" +
                                    "8. Biotechnology\n" +
                                    "9. Aeronautical Engineering\n" +
                                    "10. Automobile Engineering\n");
                            System.out.print("Enter Your Choice: ");
                            int courseId = sc.nextInt();
                            sc.nextLine();
                            String phone;
                            while (true) {
                                System.out.print("Phone: ");
                                phone = sc.nextLine().trim();
                                int len = phone.length();
                                if (len >= 10 && len <= 12) {
                                    break;
                                } else {
                                    System.out.println("Phone number is less than 10 digits or more than 12 digits. Try again.");
                                }
                            }
                            String email;
                            while (true) {
                                System.out.print("Email: ");
                                email = sc.nextLine();
                                if (email.toLowerCase().endsWith(".com")) {
                                    break;
                                } else {
                                    System.out.println("Email address is not valid. Please Enter Valid Email.");
                                }
                            }
                            System.out.print("Address: ");
                            String address = sc.nextLine();
                            System.out.print("Admission Year: ");
                            int admissionYear = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Username: ");
                            String username = sc.nextLine();
                            System.out.print("Password: ");
                            String password = sc.nextLine();
                            if (userDao.signup(type, username, email, password, fullName, gender, dob, phone, address, courseId, admissionYear)) {
                                System.out.println("Signup successful! Please wait for approval.");
                            }
                            else {
                                throw new DuplicateEntryException("Signup failed (username or email may already exist).");
                            }
                        }
                    } catch (DuplicateEntryException e) {
                        System.out.println(e.getMessage());
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error during signup: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Exiting system.");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
