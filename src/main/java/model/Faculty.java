package model;

public class Faculty {
    private int facultyId;
    private int userId;
    private String fullName;
    private String gender;
    private String dob;
    private String qualification;
    private int departmentId;
    private String phone;
    private String email;
    private String address;

    public Faculty() {}

    public Faculty(int facultyId, int userId, String fullName, String gender, String dob, String qualification,
                   int departmentId, String phone, String email, String address) {
        this.facultyId = facultyId;
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.qualification = qualification;
        this.departmentId = departmentId;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Faculty [facultyId=" + facultyId +
                ", userId=" + userId +
                ", fullName=" + fullName +
                ", gender=" + gender +
                ", dob=" + dob +
                ", qualification=" + qualification +
                ", departmentId=" + departmentId +
                ", phone=" + phone +
                ", email=" + email +
                ", address=" + address + "]";
    }
}
