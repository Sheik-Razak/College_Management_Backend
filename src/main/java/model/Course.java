package model;

public class Course {
    private int courseId;
    private String courseName;
    private int deptId;
    private int durationYears;
    private int semesterCount;

    public Course() {}

    public Course(int courseId, String courseName, int deptId, int durationYears, int semesterCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.deptId = deptId;
        this.durationYears = durationYears;
        this.semesterCount = semesterCount;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public int getDurationYears() { return durationYears; }
    public void setDurationYears(int durationYears) { this.durationYears = durationYears; }

    public int getSemesterCount() { return semesterCount; }
    public void setSemesterCount(int semesterCount) { this.semesterCount = semesterCount; }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId +
                ", courseName=" + courseName +
                ", deptId=" + deptId +
                ", durationYears=" + durationYears +
                ", semesterCount=" + semesterCount + "]";
    }
}
