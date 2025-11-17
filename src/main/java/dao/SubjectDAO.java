package dao;

import custom_exception.DatabaseConnectionException;
import model.Subject;
import java.sql.*;
import custom_exception.SubjectNotFoundException;
import custom_exception.DuplicateEntryException;

public class SubjectDAO {
    public void printSubjectsByFacultyId(int facultyId) throws SubjectNotFoundException, DatabaseConnectionException {
        String sql = "SELECT subject_id, subject_name, semester_no, credits FROM subject WHERE faculty_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\nSUBJECTS ASSIGNED:");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-30s %-10s %-8s%n", "Subject ID", "Subject Name", "Semester", "Credits");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()) {
                int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                int semesterNo = rs.getInt("semester_no");
                int credits = rs.getInt("credits");
                System.out.printf("%-10d %-30s %-10d %-8d%n", subjectId, subjectName, semesterNo, credits);
                found = true;
            }
            if (!found) {
                throw new SubjectNotFoundException("No subjects assigned yet for faculty ID: " + facultyId);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage(), e);
        }
    }

    public void getAllSubjects() throws SubjectNotFoundException, DatabaseConnectionException {
        String sql = "SELECT subject_id, subject_name, semester_no, credits FROM subject ";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            System.out.println("\nALL SUBJECTS : ");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-40s %-10s %-8s%n", "Subject ID", "Subject Name", "Semester", "Credits");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()) {
                int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                int semesterNo = rs.getInt("semester_no");
                int credits = rs.getInt("credits");
                System.out.printf("%-10d %-40s %-10d %-8d%n", subjectId, subjectName, semesterNo, credits);
                found = true;
            }
            if (!found) {
                throw new SubjectNotFoundException("No subjects found for this course.");
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage(), e);
        }
    }

    public void printAllSubjects(int courseId) throws SubjectNotFoundException, DatabaseConnectionException {
        String sql = "SELECT subject_id, subject_name, semester_no, credits FROM subject WHERE course_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\nSUBJECTS FOR COURSE ID: " + courseId);
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-40s %-10s %-8s%n", "Subject ID", "Subject Name", "Semester", "Credits");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()) {
                int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                int semesterNo = rs.getInt("semester_no");
                int credits = rs.getInt("credits");
                System.out.printf("%-10d %-40s %-10d %-8d%n", subjectId, subjectName, semesterNo, credits);
                found = true;
            }
            if (!found) {
                throw new SubjectNotFoundException("No subjects found for this course ID: " + courseId);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage(), e);
        }
    }

    public boolean insertSubject(String subjectName, int courseId, int facultyId, int semesterNo, int credits) throws DuplicateEntryException, DatabaseConnectionException {
        String sql = "INSERT INTO subject (subject_name, course_id, faculty_id, semester_no, credits) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subjectName);
            ps.setInt(2, courseId);
            ps.setInt(3, facultyId);
            ps.setInt(4, semesterNo);
            ps.setInt(5, credits);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Subject inserted successfully!");
                return true;
            } else {
                throw new DuplicateEntryException("Subject insertion failed: Duplicate subject or invalid faculty/course ID.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException("Subject insertion failed: Duplicate subject or invalid faculty/course ID.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage(), e);
        }
    }

    public boolean deleteSubject(int subjectId) throws SubjectNotFoundException, DatabaseConnectionException {
        String sql = "DELETE FROM subject WHERE subject_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Subject deleted successfully!");
                return true;
            } else {
                throw new SubjectNotFoundException("Subject not found for deletion: ID " + subjectId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage(), e);
        }
    }

    private Subject mapSubject(ResultSet rs) throws SQLException {
        Subject s = new Subject();
        s.setSubjectId(rs.getInt("subject_id"));
        s.setSubjectName(rs.getString("subject_name"));
        s.setCourseId(rs.getInt("course_id"));
        s.setSemesterNo(rs.getInt("semester_no"));
        s.setCredits(rs.getInt("credits"));
        s.setFacultyId(rs.getInt("faculty_id"));
        return s;
    }
}
