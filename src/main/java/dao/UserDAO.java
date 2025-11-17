package dao;

import model.User;
import java.sql.*;
import custom_exception.UserNotFoundException;
import custom_exception.DuplicateEntryException;
import custom_exception.DatabaseConnectionException;

public class UserDAO {

    public boolean validateLogin(String username, String password, String userType) throws DatabaseConnectionException {
        boolean valid = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM user WHERE username = ? AND password = ? AND user_type = ? AND status = 'Active'"
            );
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, userType);
            ResultSet rs = st.executeQuery();
            valid = rs.next();
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error validating login: " + e.getMessage(), e);
        }
        return valid;
    }

    // Faculty signup
    public boolean signup(String user_type,String username, String email, String password, String fullName, String gender, String dob, String phone, String address, String qualification, int departmentId)
            throws DuplicateEntryException, DatabaseConnectionException
    {
        boolean success = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO pending_user (full_name, gender, user_type, dob, phone, email, address, qualification, department_id, username,password,status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, 'Pending')"
            );
            st.setString(1, fullName);
            st.setString(2, gender);
            st.setString(3, user_type);
            st.setString(4, dob);
            st.setString(5, phone);
            st.setString(6, email);
            st.setString(7, address);
            st.setString(8, qualification);
            st.setInt(9, departmentId);
            st.setString(10, username);
            st.setString(11, password);
            try {
                int rows = st.executeUpdate();
                success = rows > 0;
            } catch (SQLIntegrityConstraintViolationException ex) {
                throw new DuplicateEntryException("Username or Email already exists.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error during signup: " + e.getMessage(), e);
        }
        return success;
    }

    // Student signup
    public boolean signup(String user_type,String username, String email, String password,
                          String fullName, String gender, String dob, String phone, String address, int courseId, int admissionYear)
            throws DuplicateEntryException, DatabaseConnectionException
    {
        boolean success = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO pending_user (full_name, gender, user_type, dob, phone, email, address, course_id, admissionYear, username,password, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, 'Pending')"
            );
            st.setString(1, fullName);
            st.setString(2, gender);
            st.setString(3, user_type);
            st.setString(4, dob);
            st.setString(5, phone);
            st.setString(6, email);
            st.setString(7, address);
            st.setInt(8, courseId);
            st.setInt(9, admissionYear);
            st.setString(10, username);
            st.setString(11, password);
            try {
                int rows = st.executeUpdate();
                success = rows > 0;
            } catch (SQLIntegrityConstraintViolationException ex) {
                throw new DuplicateEntryException("Username or Email already exists.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error during signup: " + e.getMessage(), e);
        }
        return success;
    }

    public boolean signupPending(String username, String email, String password, String userType,
                                 String fullName, String gender, String dob, String phone, String address, int courseId, int departmentId,
                                 String qualification)
            throws DuplicateEntryException, DatabaseConnectionException
    {
        boolean success = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO pending_user " +
                            "(username, email, password, user_type, full_name, gender, dob, phone, address, course_id, department_id, qualification,password, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending')"
            );
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, password);
            st.setString(4, userType);
            st.setString(5, fullName);
            st.setString(6, gender);
            st.setString(7, dob);
            st.setString(8, phone);
            st.setString(9, address);
            st.setInt(10, courseId);
            st.setInt(11, departmentId);
            st.setString(12, qualification);
            st.setString(13, password);
            try {
                int rows = st.executeUpdate();
                success = rows > 0;
            } catch (SQLIntegrityConstraintViolationException ex) {
                throw new DuplicateEntryException("Username or Email already exists.");
            }
        } catch (DuplicateEntryException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error during signup: " + e.getMessage(), e);
        }
        return success;
    }

    public User getUserByUsername(String username) throws UserNotFoundException, DatabaseConnectionException {
        User user = null;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
            rs.close();
            if (user == null) throw new UserNotFoundException("User not found for username: " + username);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error getting user: " + e.getMessage(), e);
        }
        return user;
    }

    public User getUserById(int userId) throws UserNotFoundException, DatabaseConnectionException {
        User user = null;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
            rs.close();
            if (user == null) throw new UserNotFoundException("User not found for user id: " + userId);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error getting user: " + e.getMessage(), e);
        }
        return user;
    }

    public void printAllUsers() throws DatabaseConnectionException {
        String query = "SELECT u.user_id, " +
                "COALESCE(f.full_name, s.full_name, a.full_name) AS full_name, " +
                "u.email, u.user_type, u.created_date, " +
                "COALESCE(f.gender, s.gender) AS gender, " +
                "COALESCE(f.dob, s.dob) AS dob, " +
                "COALESCE(f.address, s.address) AS address " +
                "FROM user u " +
                "LEFT JOIN faculty f ON u.user_id = f.user_id " +
                "LEFT JOIN student s ON u.user_id = s.user_id " +
                "LEFT JOIN admin a ON u.user_id = a.user_id";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            System.out.println("\nUSER LIST:");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-25s %-30s %-10s %-20s %-8s %-12s %-30s%n",
                    "ID", "Full Name", "Email", "Type", "Created Date", "Gender", "DOB", "Address");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String userType = rs.getString("user_type");
                String created = rs.getString("created_date");
                String gender = rs.getString("gender");
                String dob = rs.getString("dob");
                String address = rs.getString("address");

                System.out.printf("%-5d %-25s %-30s %-10s %-20s %-8s %-12s %-30s%n",
                        id,
                        fullName != null ? fullName : "",
                        email != null ? email : "",
                        userType != null ? userType : "",
                        created != null ? created : "",
                        gender != null ? gender : "",
                        dob != null ? dob : "",
                        address != null ? address : "");
                found = true;
            }
            if (!found) {
                System.out.println("No users found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("DB error fetching users: " + e.getMessage(), e);
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setUserType(rs.getString("user_type"));
        user.setStatus(rs.getString("status"));
        user.setCreatedDate(rs.getString("created_date"));
        user.setLastLogin(rs.getString("last_login"));
        return user;
    }
}
