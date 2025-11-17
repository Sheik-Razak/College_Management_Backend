package dao;

import custom_exception.DatabaseConnectionException;
import custom_exception.DuplicateEntryException;
import model.Attendance;
import java.sql.*;
import java.util.Scanner;
import custom_exception.AttendanceNotFoundException;

public class AttendanceDAO {

    public double getAttendancePercentage(int studentId) {
        int present = 0, total = 0;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "SELECT status FROM attendance WHERE student_id = ?"
            );
            st.setInt(1, studentId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                if ("Present".equalsIgnoreCase(status)) present++;
                total++;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (total == 0) return 0;
        return ((double)present / total) * 100;
    }

    public boolean insertAttendance(Attendance attendance) throws DuplicateEntryException, DatabaseConnectionException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO attendance (student_id, subject_id, date, status) VALUES (?, ?, ?, ?)"
            );
            st.setInt(1, attendance.getStudentId());
            st.setInt(2, attendance.getSubjectId());
            st.setString(3, attendance.getDate());
            st.setString(4, attendance.getStatus());
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException("Duplicate attendance (" + attendance.getStudentId() + ", " + attendance.getSubjectId() + ", " + attendance.getDate() + ")");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error inserting attendance: " + e.getMessage(), e);
        }
    }


    public void markAttendance(int subjectId, String date, String startTime, String endTime) throws AttendanceNotFoundException {
        String infoSql =
                "SELECT s.subject_name, c.course_name, d.dept_name, t.start_time, t.end_time " +
                        "FROM subject s " +
                        "JOIN course c ON s.course_id = c.course_id " +
                        "JOIN department d ON c.dept_id = d.dept_id " +
                        "JOIN timetable t ON s.subject_id = t.subject_id " +
                        "WHERE s.subject_id = ? AND t.start_time = ? AND t.end_time = ?";
        String studentsSql =
                "SELECT st.student_id, st.full_name " +
                        "FROM student st " +
                        "JOIN subject sb ON sb.course_id = st.course_id " +
                        "WHERE sb.subject_id = ?";

        Scanner sc = new Scanner(System.in);
        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement infoPs = con.prepareStatement(infoSql);
                PreparedStatement studentsPs = con.prepareStatement(studentsSql)
        ) {
            infoPs.setInt(1, subjectId);
            infoPs.setString(2, startTime);
            infoPs.setString(3, endTime);
            ResultSet infoRs = infoPs.executeQuery();
            if (infoRs.next()) {
                System.out.println("Subject: " + infoRs.getString("subject_name"));
                System.out.println("Course: " + infoRs.getString("course_name"));
                System.out.println("Department: " + infoRs.getString("dept_name"));
                System.out.println("Time: " + infoRs.getString("start_time") +
                        " - " + infoRs.getString("end_time"));
            } else {
                infoRs.close();
                throw new AttendanceNotFoundException("No class found for this subject/time.");
            }
            infoRs.close();
            studentsPs.setInt(1, subjectId);
            ResultSet rs = studentsPs.executeQuery();
            boolean anyStudent = false;
            while (rs.next()) {
                anyStudent = true;
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("full_name");
                System.out.print("Mark attendance for " + studentName + " (Present/Absent/Late): ");
                String status = sc.nextLine().trim();
                boolean added = insertAttendance(new Attendance(0, studentId, subjectId, date, status));
                System.out.println(added ? "Attendance marked." : "Error marking attendance.");
            }
            rs.close();
            if (!anyStudent) {
                throw new AttendanceNotFoundException("No students found for this subject.");
            }
        } catch (AttendanceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAttendanceByStudentId(int studentId) throws AttendanceNotFoundException {
        String sql = "SELECT a.attendance_id, s.full_name, sub.subject_name, a.date, a.status, sub.semester_no " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.student_id " +
                "JOIN subject sub ON a.subject_id = sub.subject_id " +
                "WHERE a.student_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nATTENDANCE RECORDS for Student ID: " + studentId);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-30s %-12s %-10s %-8s%n",
                    "ID", "Student Name", "Subject Name", "Date", "Status", "Semester");
            System.out.println("-------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int attendanceId = rs.getInt("attendance_id");
                String studentName = rs.getString("full_name");
                String subjectName = rs.getString("subject_name");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                int semester = rs.getInt("semester_no");

                System.out.printf("%-5d %-20s %-30s %-12s %-10s %-8d%n",
                        attendanceId, studentName, subjectName, date != null ? date.toString() : "", status, semester);
                found = true;
            }
            if (!found) {
                throw new AttendanceNotFoundException("No attendance records found for this student.");
            }
            rs.close();
        } catch (AttendanceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAllAttendance() throws AttendanceNotFoundException {
        String sql = "SELECT a.attendance_id, s.full_name, sub.subject_name, a.date, a.status, sub.semester_no " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.student_id " +
                "JOIN subject sub ON a.subject_id = sub.subject_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nATTENDANCE RECORDS:");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-30s %-12s %-10s %-8s%n",
                    "ID", "Student Name", "Subject Name", "Date", "Status", "Semester");
            System.out.println("-------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int attendanceId = rs.getInt("attendance_id");
                String studentName = rs.getString("full_name");
                String subjectName = rs.getString("subject_name");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                int semester = rs.getInt("semester_no");

                System.out.printf("%-5d %-20s %-30s %-12s %-10s %-8d%n",
                        attendanceId, studentName, subjectName, date != null ? date.toString() : "", status, semester);
                found = true;
            }
            if (!found) {
                throw new AttendanceNotFoundException("No attendance records found.");
            }
        } catch (AttendanceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Attendance mapAttendance(ResultSet rs) throws SQLException {
        Attendance a = new Attendance();
        a.setAttendanceId(rs.getInt("attendance_id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setSubjectId(rs.getInt("subject_id"));
        a.setDate(rs.getString("date"));
        a.setStatus(rs.getString("status"));
        return a;
    }
}
