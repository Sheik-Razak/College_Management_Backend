package model;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String userType;
    private String status;
    private String createdDate;
    private String lastLogin;

    public User() {}

    public User(int userId, String username, String email, String password, String userType,
                String status, String createdDate, String lastLogin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.status = status;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", email=" + email +
                ", userType=" + userType + ", status=" + status +
                ", createdDate=" + createdDate + ", lastLogin=" + lastLogin + "]";
    }
}
