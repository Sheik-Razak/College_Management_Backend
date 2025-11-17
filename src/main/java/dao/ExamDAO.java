package dao;

import model.Exam;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import custom_exception.DatabaseConnectionException;
import custom_exception.DuplicateEntryException;

public class ExamDAO {

    public List<Exam> getExamsByCourseAndSubject(int courseId, int subjectId) throws DatabaseConnectionException {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT e.exam_id, e.course_id, e.subject_id, s.subject_name, e.exam_date, e.exam_type, e.total_marks " +
                "FROM exam e " +
                "JOIN subject s ON e.subject_id = s.subject_id " +
                "WHERE e.course_id = ? AND e.subject_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, courseId);
            st.setInt(2, subjectId);
            ResultSet rs = st.executeQuery();
            boolean found = false;
            while (rs.next()) {
                exams.add(mapExam(rs));
                found = true;
            }
            rs.close();
            if (!found) {
                System.out.println("No exams found for the given course and subject.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error fetching exams: " + e.getMessage(), e);
        }
        return exams;
    }

    public boolean deleteExam(int examId) throws DatabaseConnectionException {
        String sql = "DELETE FROM exam WHERE exam_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, examId);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error deleting exam: " + e.getMessage(), e);
        }
    }

    public boolean insertExam(Exam exam) throws DuplicateEntryException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO exam (course_id, subject_id, exam_date, exam_type, total_marks) VALUES (?, ?, ?, ?, ?)"
            );
            st.setInt(1, exam.getCourseId());
            st.setInt(2, exam.getSubjectId());
            st.setString(3, exam.getExamDate());
            st.setString(4, exam.getExamType());
            st.setInt(5, exam.getTotalMarks());
            try {
                int rows = st.executeUpdate();
                if (rows == 0) throw new DuplicateEntryException("Exam already exists or subject not found.");
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new DuplicateEntryException("Exam already exists or subject not found.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error inserting exam: " + e.getMessage(), e);
        }
    }

    public void getAllExams() throws DatabaseConnectionException {
        String sql = "SELECT e.exam_id, c.course_name, s.subject_name, e.exam_date, e.exam_type, e.total_marks " +
                "FROM exam e " +
                "JOIN course c ON e.course_id = c.course_id " +
                "JOIN subject s ON e.subject_id = s.subject_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nEXAM LIST:");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-8s %-45s %-40s %-12s %-15s %-12s%n",
                    "ExamID", "Course Name", "Subject Name", "Date", "Type", "Total Marks");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int examId = rs.getInt("exam_id");
                String courseName = rs.getString("course_name");
                String subjectName = rs.getString("subject_name");
                Date examDate = rs.getDate("exam_date");
                String examType = rs.getString("exam_type");
                int totalMarks = rs.getInt("total_marks");

                System.out.printf("%-8d %-45s %-40s %-12s %-15s %-12d%n",
                        examId, courseName, subjectName,
                        examDate != null ? examDate.toString() : "",
                        examType, totalMarks);
                found = true;
            }
            if (!found) {
                System.out.println("No exams found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error getting all exams: " + e.getMessage(), e);
        }
    }

    private Exam mapExam(ResultSet rs) throws SQLException {
        Exam e = new Exam();
        e.setExamId(rs.getInt("exam_id"));
        e.setCourseId(rs.getInt("course_id"));
        e.setSubjectId(rs.getInt("subject_id"));
        e.setExamDate(rs.getString("exam_date"));
        e.setExamType(rs.getString("exam_type"));
        e.setTotalMarks(rs.getInt("total_marks"));
        return e;
    }
}
