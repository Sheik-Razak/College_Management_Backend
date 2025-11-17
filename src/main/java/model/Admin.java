package model;

public class Admin {
    private int adminId;
    private int userId;
    private String fullName;
    private String phone;
    private String roleLevel;

    public Admin() {}

    public Admin(int adminId, int userId, String fullName, String phone, String roleLevel) {
        this.adminId = adminId;
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.roleLevel = roleLevel;
    }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRoleLevel() { return roleLevel; }
    public void setRoleLevel(String roleLevel) { this.roleLevel = roleLevel; }

    @Override
    public String toString() {
        return "Admin [adminId=" + adminId +
                ", userId=" + userId +
                ", fullName=" + fullName +
                ", phone=" + phone +
                ", roleLevel=" + roleLevel + "]";
    }
}
