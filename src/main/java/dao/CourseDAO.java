package dao;

import model.Course;
import java.sql.*;
import custom_exception.DatabaseConnectionException;

public class CourseDAO {

    public void printAllCourses() throws DatabaseConnectionException {
        String sql = "SELECT c.course_id, c.course_name, d.dept_name, c.duration_years, c.semester_count " +
                "FROM course c JOIN department d ON c.dept_id = d.dept_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nCOURSE LIST:");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-40s %-40s %-10s %-10s%n",
                    "ID", "Course Name", "Department", "Years", "Semesters");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                String deptName = rs.getString("dept_name");
                int years = rs.getInt("duration_years");
                int semesters = rs.getInt("semester_count");

                System.out.printf("%-5d %-40s %-40s %-10d %-10d%n",
                        courseId, courseName, deptName, years, semesters);
                found = true;
            }
            if (!found) {
                System.out.println("No courses found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error getting all courses: " + e.getMessage(), e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setCourseName(rs.getString("course_name"));
        c.setDeptId(rs.getInt("dept_id"));
        c.setDurationYears(rs.getInt("duration_years"));
        c.setSemesterCount(rs.getInt("semester_count"));
        return c;
    }
}
