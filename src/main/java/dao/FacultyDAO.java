package dao;

import model.Faculty;
import java.sql.*;
import custom_exception.DatabaseConnectionException;
import custom_exception.FacultyNotFoundException;

public class FacultyDAO {

    public boolean deleteFacultyById(int facultyId) throws FacultyNotFoundException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement("DELETE FROM faculty WHERE faculty_id = ?");
            st.setInt(1, facultyId);
            int rows = st.executeUpdate();
            if (rows == 0) throw new FacultyNotFoundException("Faculty not found for ID: " + facultyId);
            return true;
        } catch (FacultyNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error deleting faculty: " + e.getMessage(), e);
        }
    }

    public Faculty getFacultyByIdOrUserId(int id) throws DatabaseConnectionException {
        Faculty faculty = null;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM faculty WHERE faculty_id = ? OR user_id = ?"
            );
            st.setInt(1, id);
            st.setInt(2, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                faculty = mapFaculty(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error retrieving faculty: " + e.getMessage(), e);
        }
        return faculty;
    }

    public void printAllFaculty() throws DatabaseConnectionException {
        String sql = "SELECT f.faculty_id, f.user_id, f.full_name, f.gender, f.dob, f.qualification, " +
                "d.dept_name, f.phone, f.email, f.address " +
                "FROM faculty f JOIN department d ON f.department_id = d.dept_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nFACULTY LIST:");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-8s %-20s %-8s %-12s %-20s %-40s %-15s %-30s %-30s%n",
                    "ID", "UserID", "Full Name", "Gender", "DOB", "Qualification", "Department", "Phone", "Email", "Address");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int facultyId = rs.getInt("faculty_id");
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                String qualification = rs.getString("qualification");
                String deptName = rs.getString("dept_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                System.out.printf("%-5d %-8d %-20s %-8s %-12s %-20s %-40s %-15s %-30s %-30s%n",
                        facultyId, userId, fullName, gender,
                        dob != null ? dob.toString() : "",
                        qualification, deptName, phone, email, address);
                found = true;
            }
            if (!found) {
                System.out.println("No faculty found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error fetching faculty: " + e.getMessage(), e);
        }
    }

    private Faculty mapFaculty(ResultSet rs) throws SQLException {
        Faculty f = new Faculty();
        f.setFacultyId(rs.getInt("faculty_id"));
        f.setUserId(rs.getInt("user_id"));
        f.setFullName(rs.getString("full_name"));
        f.setGender(rs.getString("gender"));
        f.setDob(rs.getString("dob"));
        f.setQualification(rs.getString("qualification"));
        f.setDepartmentId(rs.getInt("department_id"));
        f.setPhone(rs.getString("phone"));
        f.setEmail(rs.getString("email"));
        f.setAddress(rs.getString("address"));
        return f;
    }
}
