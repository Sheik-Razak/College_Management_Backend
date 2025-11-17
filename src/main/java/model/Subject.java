package model;

public class Subject {
    private int subjectId;
    private String subjectName;
    private int courseId;
    private int semesterNo;
    private int credits;
    private int facultyId;

    public Subject() {}

    public Subject(int subjectId,String subjectName,int courseId){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.courseId = courseId;
    }
    public Subject(int subjectId, String subjectName, int courseId, int semesterNo, int credits, int facultyId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.courseId = courseId;
        this.semesterNo = semesterNo;
        this.credits = credits;
        this.facultyId = facultyId;
    }

    public int getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getSemesterNo() { return semesterNo; }
    public void setSemesterNo(int semesterNo) { this.semesterNo = semesterNo; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    @Override
    public String toString() {
        return "Subject [subjectId=" + subjectId +
                ", subjectName=" + subjectName +
                ", courseId=" + courseId +
                ", semesterNo=" + semesterNo +
                ", credits=" + credits + "]";
    }
}
