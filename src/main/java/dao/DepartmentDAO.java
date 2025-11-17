package dao;

import model.Department;
import java.sql.*;
import custom_exception.DatabaseConnectionException;
import custom_exception.DepartmentNotFoundException;
import custom_exception.DuplicateEntryException;

public class DepartmentDAO {

    public boolean deleteDepartment(int deptId) throws DepartmentNotFoundException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement("DELETE FROM department WHERE dept_id = ?")) {
            st.setInt(1, deptId);
            int rows = st.executeUpdate();
            if (rows == 0) throw new DepartmentNotFoundException("Department not found for ID: " + deptId);
            return true;
        } catch (DepartmentNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error when deleting department: " + e.getMessage(), e);
        }
    }

    public boolean insertDepartment(Department dept) throws DuplicateEntryException, DatabaseConnectionException {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO department (dept_name, dept_head, office_location) VALUES (?, ?, ?)"
            );
            st.setString(1, dept.getDeptName());
            st.setString(2, dept.getDeptHead());
            st.setString(3, dept.getOfficeLocation());
            try {
                int rows = st.executeUpdate();
                if (rows == 0) throw new DuplicateEntryException("Department name already exists.");
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new DuplicateEntryException("Department name already exists.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error when inserting department: " + e.getMessage(), e);
        }
    }

    public void printAllDepartments() throws DatabaseConnectionException {
        String sql = "SELECT * FROM department";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nDEPARTMENT LIST:");
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-40s %-25s %-20s%n",
                    "ID", "Department Name", "Department Head", "Office Location");
            System.out.println("-----------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                String deptHead = rs.getString("dept_head");
                String officeLocation = rs.getString("office_location");

                System.out.printf("%-5d %-40s %-25s %-20s%n",
                        deptId, deptName, deptHead, officeLocation);
                found = true;
            }
            if (!found) {
                System.out.println("No departments found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error when fetching departments: " + e.getMessage(), e);
        }
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setDeptId(rs.getInt("dept_id"));
        d.setDeptName(rs.getString("dept_name"));
        d.setDeptHead(rs.getString("dept_head"));
        d.setOfficeLocation(rs.getString("office_location"));
        return d;
    }
}
