package dao;

import model.Admin;
import java.sql.*;
import custom_exception.DatabaseConnectionException;

public class AdminDAO {

    public void printAllAdmins() {
        try (Connection con = DBUtil.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM admin");
            boolean found = false;
            while (rs.next()) {
                Admin admin = mapAdmin(rs);
                System.out.println(
                        "AdminId: " + admin.getAdminId() +
                                ", UserId: " + admin.getUserId() +
                                ", FullName: " + admin.getFullName() +
                                ", Phone: " + admin.getPhone() +
                                ", RoleLevel: " + admin.getRoleLevel()
                );
                found = true;
            }
            if (!found) {
                System.out.println("No admin records found.");
            }
            rs.close();
            st.close();
        } catch (DatabaseConnectionException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Admin mapAdmin(ResultSet rs) throws SQLException {
        Admin a = new Admin();
        a.setAdminId(rs.getInt("admin_id"));
        a.setUserId(rs.getInt("user_id"));
        a.setFullName(rs.getString("full_name"));
        a.setPhone(rs.getString("phone"));
        a.setRoleLevel(rs.getString("role_level"));
        return a;
    }
}
