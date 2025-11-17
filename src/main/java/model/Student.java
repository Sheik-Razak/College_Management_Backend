package model;

public class Student {
    private int studentId;
    private int userId;
    private String fullName;
    private String gender;
    private String dob;
    private int courseId;
    private String phone;
    private String email;
    private String address;
    private int admissionYear;

    public Student() {}

    public Student(int studentId, int userId, String fullName, String gender, String dob, int courseId,
                   String phone, String email, String address, int admissionYear) {
        this.studentId = studentId;
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.courseId = courseId;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.admissionYear = admissionYear;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(int admissionYear) { this.admissionYear = admissionYear; }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId +
                ", userId=" + userId +
                ", fullName=" + fullName +
                ", gender=" + gender +
                ", dob=" + dob +
                ", courseId=" + courseId +
                ", phone=" + phone +
                ", email=" + email +
                ", address=" + address +
                ", admissionYear=" + admissionYear + "]";
    }
}
