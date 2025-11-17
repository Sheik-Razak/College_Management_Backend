package dao;

import model.Result;
import java.sql.*;
import java.util.Scanner;
import custom_exception.DatabaseConnectionException;
import custom_exception.DuplicateEntryException;

public class ResultDAO {
    Scanner sc = new Scanner(System.in);

    public Integer getExamId(int subjectId, String type) throws DatabaseConnectionException {
        Integer examId = null;
        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT exam_id FROM exam WHERE subject_id = ? AND exam_type = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, subjectId);
            st.setString(2, type);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                examId = rs.getInt("exam_id");
            }
            rs.close(); st.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error finding exam ID: " + e.getMessage(), e);
        }
        return examId;
    }

    public boolean insertAssessmentResult(int studentId, int subjectId, int marksObtained, String grade)
            throws DatabaseConnectionException, DuplicateEntryException {
        String type = "Assessment";
        Integer examId = getExamId(subjectId, type);
        if (examId == null) {
            System.out.println("No assessment exam found for the given subject.");
            return false;
        }
        Result rs = new Result(studentId, examId, marksObtained, grade);
        return insertResult(rs);
    }

    public boolean insertMidTermResult(int studentId, int subjectId, int marksObtained, String grade)
            throws DatabaseConnectionException, DuplicateEntryException {
        String type = "Midterm";
        Integer examId = getExamId(subjectId, type);
        if (examId == null) {
            System.out.println("No MidTerm exam found for the given subject.");
            return false;
        }
        Result rs = new Result(studentId, examId, marksObtained, grade);
        return insertResult(rs);
    }

    public boolean insertFinalResult(int studentId, int subjectId, int marksObtained, String grade)
            throws DatabaseConnectionException, DuplicateEntryException {
        String type = "Final";
        Integer examId = getExamId(subjectId, type);
        if (examId == null) {
            System.out.println("No final exam found for the given subject.");
            return false;
        }
        Result rs = new Result(studentId, examId, marksObtained, grade);
        return insertResult(rs);
    }

    public void enterAssessmentMarksForSubject() {
        try {
            System.out.print("Enter the subject_id for assessment: ");
            int subjectId = Integer.parseInt(sc.nextLine().trim());
            try (Connection con = DBUtil.getConnection()) {
                String query = "SELECT s.student_id, s.full_name FROM student s " +
                        "JOIN subject sub ON s.course_id = sub.course_id WHERE sub.subject_id = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, subjectId);
                ResultSet rs = stmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int studentId = rs.getInt("student_id");
                    String studentName = rs.getString("full_name");
                    System.out.println("Enter marks for student: " + studentName + " (ID: " + studentId + ")");
                    System.out.print("Marks obtained (or type 'skip' for absent): ");
                    String input = sc.nextLine();

                    int marksObtained = 0;
                    String grade;

                    if (input.equalsIgnoreCase("skip")) {
                        marksObtained = 0;
                        grade = calculateAssessmentGrade(marksObtained);
                    } else {
                        marksObtained = Integer.parseInt(input);
                        grade = calculateAssessmentGrade(marksObtained);
                    }

                    try {
                        boolean success = insertAssessmentResult(studentId, subjectId, marksObtained, grade);
                        System.out.println(success ? "Marks inserted." : "Insertion failed.");
                    } catch (DatabaseConnectionException e) {
                        System.out.println("DB error: " + e.getMessage());
                    } catch (DuplicateEntryException e) {
                        System.out.println("Duplicate error: " + e.getMessage());
                    }
                }
                if (!found) {
                    System.out.println("No students found for subject_id " + subjectId);
                }
                rs.close();
                stmt.close();
            } catch (DatabaseConnectionException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
    }

    private String calculateAssessmentGrade(int marks) {
        if (marks >= 0 && marks <= 5) return "F";
        if (marks >= 6 && marks <= 10) return "C";
        if (marks >= 11 && marks <= 15) return "B";
        if (marks >= 16 && marks <= 17) return "A";
        if (marks >= 18 && marks <= 19) return "A+";
        if (marks == 20) return "O";
        return "Invalid";
    }

    public void enterMidtermMarksForSubject() {
        try {
            System.out.print("Enter the subject_id for midterm: ");
            int subjectId = Integer.parseInt(sc.nextLine().trim());
            try (Connection con = DBUtil.getConnection()) {
                String query = "SELECT s.student_id, s.full_name FROM student s " +
                        "JOIN subject sub ON s.course_id = sub.course_id WHERE sub.subject_id = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, subjectId);
                ResultSet rs = stmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int studentId = rs.getInt("student_id");
                    String studentName = rs.getString("full_name");
                    System.out.println("Enter marks for student: " + studentName + " (ID: " + studentId + ")");
                    System.out.print("Marks obtained (or type 'skip' for absent): ");
                    String input = sc.nextLine().trim();

                    int marksObtained = 0;
                    String grade;

                    if (input.equalsIgnoreCase("skip")) {
                        marksObtained = 0;
                        grade = calculateAssessmentGrade(marksObtained);
                    } else {
                        marksObtained = Integer.parseInt(input);
                        grade = calculateGrade(marksObtained);
                    }

                    try {
                        boolean success = insertMidTermResult(studentId, subjectId, marksObtained, grade);
                        System.out.println(success ? "Marks inserted." : "Insertion failed.");
                    } catch (DatabaseConnectionException e) {
                        System.out.println("DB error: " + e.getMessage());
                    } catch (DuplicateEntryException e) {
                        System.out.println("Duplicate error: " + e.getMessage());
                    }
                }
                if (!found) {
                    System.out.println("No students found for subject_id " + subjectId);
                }
                rs.close();
                stmt.close();
            } catch (DatabaseConnectionException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
    }

    public void enterFinalMarksForSubject() {
        try {
            System.out.print("Enter the subject_id for final: ");
            int subjectId = Integer.parseInt(sc.nextLine().trim());
            try (Connection con = DBUtil.getConnection()) {
                String query = "SELECT s.student_id, s.full_name FROM student s JOIN subject sub ON s.course_id = sub.course_id WHERE sub.subject_id = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, subjectId);
                ResultSet rs = stmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int studentId = rs.getInt("student_id");
                    String studentName = rs.getString("full_name");
                    System.out.println("Enter marks for student: " + studentName + " (ID: " + studentId + ")");
                    System.out.print("Marks obtained (or type 'skip' for absent): ");
                    String input = sc.nextLine().trim();

                    int marksObtained = 0;
                    String grade;

                    if (input.equalsIgnoreCase("skip")) {
                        marksObtained = 0;
                        grade = calculateAssessmentGrade(marksObtained);
                    } else {
                        marksObtained = Integer.parseInt(input);
                        grade = calculateGrade(marksObtained);
                    }

                    try {
                        boolean success = insertFinalResult(studentId, subjectId, marksObtained, grade);
                        System.out.println(success ? "Marks inserted." : "Insertion failed.");
                    } catch (DatabaseConnectionException e) {
                        System.out.println("DB error: " + e.getMessage());
                    } catch (DuplicateEntryException e) {
                        System.out.println("Duplicate error: " + e.getMessage());
                    }
                }
                if (!found) {
                    System.out.println("No students found for subject_id " + subjectId);
                }
                rs.close();
                stmt.close();
            } catch (DatabaseConnectionException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
    }

    private String calculateGrade(int marks) {
        if (marks >= 96 && marks <= 100) return "O";
        if (marks >= 91 && marks <= 95) return "A+";
        if (marks >= 81 && marks <= 90) return "A";
        if (marks >= 71 && marks <= 80) return "B";
        if (marks >= 61 && marks <= 70) return "C";
        if (marks >= 35 && marks <= 60) return "D";
        if (marks >= 0 && marks <= 34) return "F";
        return "Invalid";
    }

    public boolean insertResult(Result result) throws DatabaseConnectionException, DuplicateEntryException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO result (student_id, exam_id, marks_obtained, grade) VALUES (?, ?, ?, ?)"
            );
            st.setInt(1, result.getStudentId());
            st.setInt(2, result.getExamId());
            st.setInt(3, result.getMarksObtained());
            st.setString(4, result.getGrade());
            try {
                int rows = st.executeUpdate();
                return rows > 0;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new DuplicateEntryException("Result for this student/exam already exists.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error inserting result: " + e.getMessage(), e);
        }
    }

    public void getResultsByStudentId(int studentId, String type) {
        String sql = "SELECT r.result_id, s.full_name AS student_name, sub.subject_name, r.marks_obtained, r.grade " +
                "FROM result r " +
                "JOIN exam e ON r.exam_id = e.exam_id " +
                "JOIN student s ON r.student_id = s.student_id " +
                "JOIN subject sub ON e.subject_id = sub.subject_id " +
                "WHERE e.exam_type = ? AND r.student_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setInt(2, studentId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nResult ID |  Student Name           | Subject Name            | Marks Obtained | Grade");
            System.out.println("--------------------------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()) {
                System.out.printf("%-10d | %-22s | %-22s | %-14d | %-5s%n",
                        rs.getInt("result_id"),
                        rs.getString("student_name"),
                        rs.getString("subject_name"),
                        rs.getInt("marks_obtained"),
                        rs.getString("grade"));
                found = true;
            }
            if (!found) {
                System.out.println("No results found for this student.");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assessmentResults() {
        String sql = "SELECT r.result_id, s.full_name AS student_name, sub.subject_name, r.marks_obtained, r.grade " +
                "FROM result r " +
                "JOIN exam e ON r.exam_id = e.exam_id " +
                "JOIN student s ON r.student_id = s.student_id " +
                "JOIN subject sub ON e.subject_id = sub.subject_id " +
                "WHERE e.exam_type = 'Assessment'";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nASSESSMENT RESULTS:");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-25s %-15s %-8s%n", "Result ID", "Student Name", "Subject Name", "Marks", "Grade");
            System.out.println("---------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int resultId = rs.getInt("result_id");
                String studentName = rs.getString("student_name");
                String subjectName = rs.getString("subject_name");
                int marks = rs.getInt("marks_obtained");
                String grade = rs.getString("grade");

                System.out.printf("%-10d %-25s %-25s %-15d %-8s%n", resultId, studentName, subjectName, marks, grade);
                found = true;
            }
            if (!found) {
                System.out.println("No assessment results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void midtermResults() {
        String sql = "SELECT r.result_id, s.full_name AS student_name, sub.subject_name, r.marks_obtained, r.grade " +
                "FROM result r " +
                "JOIN exam e ON r.exam_id = e.exam_id " +
                "JOIN student s ON r.student_id = s.student_id " +
                "JOIN subject sub ON e.subject_id = sub.subject_id " +
                "WHERE e.exam_type = 'Midterm'";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nMIDTERM RESULTS:");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-25s %-15s %-8s%n", "Result ID", "Student Name", "Subject Name", "Marks", "Grade");
            System.out.println("---------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int resultId = rs.getInt("result_id");
                String studentName = rs.getString("student_name");
                String subjectName = rs.getString("subject_name");
                int marks = rs.getInt("marks_obtained");
                String grade = rs.getString("grade");

                System.out.printf("%-10d %-25s %-25s %-15d %-8s%n", resultId, studentName, subjectName, marks, grade);
                found = true;
            }
            if (!found) {
                System.out.println("No midterm results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finalExamResults() {
        String sql = "SELECT r.result_id, s.full_name AS student_name, sub.subject_name, r.marks_obtained, r.grade " +
                "FROM result r " +
                "JOIN exam e ON r.exam_id = e.exam_id " +
                "JOIN student s ON r.student_id = s.student_id " +
                "JOIN subject sub ON e.subject_id = sub.subject_id " +
                "WHERE e.exam_type = 'Final'";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nFINAL RESULTS:");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-25s %-15s %-8s%n", "Result ID", "Student Name", "Subject Name", "Marks", "Grade");
            System.out.println("---------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int resultId = rs.getInt("result_id");
                String studentName = rs.getString("student_name");
                String subjectName = rs.getString("subject_name");
                int marks = rs.getInt("marks_obtained");
                String grade = rs.getString("grade");

                System.out.printf("%-10d %-25s %-25s %-15d %-8s%n", resultId, studentName, subjectName, marks, grade);
                found = true;
            }
            if (!found) {
                System.out.println("No final exam results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Result mapResult(ResultSet rs) throws SQLException {
        Result r = new Result();
        r.setResultId(rs.getInt("result_id"));
        r.setStudentId(rs.getInt("student_id"));
        r.setExamId(rs.getInt("exam_id"));
        r.setMarksObtained(rs.getInt("marks_obtained"));
        r.setGrade(rs.getString("grade"));
        return r;
    }
}
