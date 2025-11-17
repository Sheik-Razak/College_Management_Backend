package model;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private int subjectId;
    private String date;
    private String status;

    public Attendance() {}

    public Attendance(int attendanceId, int studentId, int subjectId, String date, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Attendance [attendanceId=" + attendanceId +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", date=" + date +
                ", status=" + status + "]";
    }
}
