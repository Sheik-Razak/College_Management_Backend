package dao;

import custom_exception.DatabaseConnectionException;
import model.Subject;
import model.Student;
import model.Timetable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import custom_exception.TimetableNotFoundException;

public class TimetableDAO {

    public void printTimetableByFacultyId(int facultyId) throws TimetableNotFoundException, DatabaseConnectionException {
        String sql = "SELECT t.day_of_week, t.start_time, t.end_time, t.room_no, " +
                "s.subject_name " +
                "FROM timetable t " +
                "JOIN subject s ON t.subject_id = s.subject_id " +
                "WHERE t.faculty_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, facultyId);
            ResultSet rs = st.executeQuery();

            System.out.println("\nTIMETABLE FOR FACULTY ID: " + facultyId);
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-10s %-10s %-20s %-30s%n",
                    "Day", "Start", "End", "Room No", "Subject Name");
            System.out.println("---------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                String dayOfWeek = rs.getString("day_of_week");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String roomNo = rs.getString("room_no");
                String subjectName = rs.getString("subject_name");
                System.out.printf("%-15s %-10s %-10s %-20s %-30s%n",
                        dayOfWeek, startTime, endTime, roomNo, subjectName);
                found = true;
            }
            if (!found) {
                throw new TimetableNotFoundException("No timetable entries found for this faculty.");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public boolean insertTimetable(Timetable tt) throws DatabaseConnectionException {
        boolean success = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO timetable (course_id, subject_id, faculty_id, day_of_week, start_time, end_time, room_no) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            st.setInt(1, tt.getCourseId());
            st.setInt(2, tt.getSubjectId());
            st.setInt(3, tt.getFacultyId());
            st.setString(4, tt.getDayOfWeek());
            st.setString(5, tt.getStartTime());
            st.setString(6, tt.getEndTime());
            st.setString(7, tt.getRoomNo());
            int rows = st.executeUpdate();
            success = rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return success;
    }

    public Timetable getTimetableById(int subjectId) throws TimetableNotFoundException, DatabaseConnectionException {
        Timetable tt = null;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement("SELECT * FROM timetable WHERE subject_id = ?");
            st.setInt(1, subjectId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                tt = mapTimetable(rs);
            } else {
                throw new TimetableNotFoundException("No timetable found for subject ID: " + subjectId);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return tt;
    }

    public void showDefaultStudentTimetable(int studentId) throws TimetableNotFoundException, DatabaseConnectionException {
        String studentSql = "SELECT course_id FROM student WHERE student_id = ?";
        String timetableSql = "SELECT tt.day_of_week, tt.start_time, tt.end_time, " +
                "s.subject_name, f.full_name " +
                "FROM timetable tt " +
                "JOIN subject s ON tt.subject_id = s.subject_id " +
                "LEFT JOIN faculty f ON tt.faculty_id = f.faculty_id " +
                "WHERE tt.course_id = ? " +
                "ORDER BY FIELD(tt.day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'), tt.start_time";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps1 = con.prepareStatement(studentSql)) {
            ps1.setInt(1, studentId);
            try (ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next()) {
                    int courseId = rs1.getInt("course_id");
                    try (PreparedStatement ps2 = con.prepareStatement(timetableSql)) {
                        ps2.setInt(1, courseId);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            System.out.println("\nTIMETABLE:");
                            System.out.println("------------------------------------------------------------------------------------------");
                            System.out.printf("%-10s %-10s %-10s %-30s%n",
                                    "Day", "Start", "End", "Subject");
                            System.out.println("------------------------------------------------------------------------------------------");
                            boolean found = false;
                            while (rs2.next()) {
                                String day = rs2.getString("day_of_week");
                                String start = rs2.getString("start_time");
                                String end = rs2.getString("end_time");
                                String subject = rs2.getString("subject_name");
                                System.out.printf("%-10s %-10s %-10s %-30s%n",
                                        day, start, end, subject);
                                found = true;
                            }
                            if (!found) {
                                throw new TimetableNotFoundException("No timetable entries found for this student.");
                            }
                        }
                    }
                } else {
                    throw new TimetableNotFoundException("Student not found!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public List<Subject> getSubjectsForFacultyInTimetable(int facultyId) throws DatabaseConnectionException {
        List<Subject> subjectList = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "SELECT s.subject_id, s.subject_name, s.course_id " +
                            "FROM timetable t " +
                            "JOIN subject s ON t.subject_id = s.subject_id " +
                            "WHERE t.faculty_id = ? " +
                            "GROUP BY s.subject_id, s.subject_name, s.course_id"
            );
            st.setInt(1, facultyId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCourseId(rs.getInt("course_id"));
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return subjectList;
    }

    public void getAllTimetables() throws TimetableNotFoundException, DatabaseConnectionException {
        String sql = "SELECT t.timetable_id, t.course_id, t.subject_id, t.faculty_id, t.day_of_week, t.start_time, t.end_time, t.room_no FROM timetable t";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nALL TIMETABLE ENTRIES");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-10s %-12s %-10s %-12s %-10s %-10s %-10s%n",
                    "ID", "Course ID", "Subject ID", "Faculty ID", "Day", "Start", "End", "Room No");
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int timetableId = rs.getInt("timetable_id");
                int courseId = rs.getInt("course_id");
                int subjectId = rs.getInt("subject_id");
                int facultyId = rs.getInt("faculty_id");
                String dayOfWeek = rs.getString("day_of_week");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String roomNo = rs.getString("room_no");

                System.out.printf("%-5d %-10d %-12d %-10d %-12s %-10s %-10s %-10s%n",
                        timetableId, courseId, subjectId, facultyId, dayOfWeek, startTime, endTime, roomNo);
                found = true;
            }
            if (!found) {
                throw new TimetableNotFoundException("No timetable entries found.");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private Timetable mapTimetable(ResultSet rs) throws SQLException {
        Timetable t = new Timetable();
        t.setTimetableId(rs.getInt("timetable_id"));
        t.setCourseId(rs.getInt("course_id"));
        t.setSubjectId(rs.getInt("subject_id"));
        t.setFacultyId(rs.getInt("faculty_id"));
        t.setDayOfWeek(rs.getString("day_of_week"));
        t.setStartTime(rs.getString("start_time"));
        t.setEndTime(rs.getString("end_time"));
        t.setRoomNo(rs.getString("room_no"));
        return t;
    }
}
