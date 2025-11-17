package dao;

import model.Student;
import java.sql.*;
import custom_exception.StudentNotFoundException;
import custom_exception.DatabaseConnectionException;

public class StudentDAO {

    public boolean deleteStudentById(int studentId) throws StudentNotFoundException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement("DELETE FROM student WHERE student_id = ?");
            st.setInt(1, studentId);
            int rows = st.executeUpdate();
            if (rows == 0) throw new StudentNotFoundException("Student not found for ID: " + studentId);
            return true;
        } catch (StudentNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error deleting student: " + e.getMessage(), e);
        }
    }

    public Student getStudentByUserId(int userId) throws DatabaseConnectionException {
        Student student = null;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM student WHERE user_id = ?"
            );
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = mapStudent(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error retrieving student: " + e.getMessage(), e);
        }
        return student;
    }

    public void printAllStudents() throws DatabaseConnectionException {
        String sql = "SELECT s.student_id, s.user_id, s.full_name, s.gender, s.dob, " +
                "c.course_name, s.phone, s.email, s.address, s.admission_year " +
                "FROM student s JOIN course c ON s.course_id = c.course_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nSTUDENT LIST:");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-8s %-20s %-8s %-12s %-40s %-15s %-30s %-30s %-8s%n",
                    "ID", "UserID", "Full Name", "Gender", "DOB", "Course", "Phone", "Email", "Address", "Admission Year");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                String courseName = rs.getString("course_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int admissionYear = rs.getInt("admission_year");

                System.out.printf("%-5d %-8d %-20s %-8s %-12s %-40s %-15s %-30s %-30s %-8d%n",
                        studentId, userId, fullName, gender,
                        dob != null ? dob.toString() : "",
                        courseName, phone, email, address, admissionYear);
                found = true;
            }
            if (!found) {
                System.out.println("No students found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error fetching students: " + e.getMessage(), e);
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setUserId(rs.getInt("user_id"));
        s.setFullName(rs.getString("full_name"));
        s.setGender(rs.getString("gender"));
        s.setDob(rs.getString("dob"));
        s.setCourseId(rs.getInt("course_id"));
        s.setPhone(rs.getString("phone"));
        s.setEmail(rs.getString("email"));
        s.setAddress(rs.getString("address"));
        s.setAdmissionYear(rs.getInt("admission_year"));
        return s;
    }
}
