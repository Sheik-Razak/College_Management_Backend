package model;

public class Exam {
    private int examId;
    private int courseId;
    private int subjectId;
    private String examDate;
    private String examType;
    private int totalMarks;

    public Exam() {}

    public Exam (int courseId, int subjectId, String examDate, String examType, int totalMarks){
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.examDate = examDate;
        this.examType = examType;
        this.totalMarks = totalMarks;
    }
    public Exam(int examId, int courseId, int subjectId, String examDate, String examType,int totalMarks){
        this.examId = examId;
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.examDate = examDate;
        this.examType = examType;
        this.totalMarks = totalMarks;
    }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public String getExamDate() { return examDate; }
    public void setExamDate(String examDate) { this.examDate = examDate; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    @Override
    public String toString() {
        return "Exam [examId=" + examId +
                ", courseId=" + courseId +
                ", subjectId=" + subjectId +
                ", examDate=" + examDate +
                ", examType=" + examType +
                ", totalMarks=" + totalMarks + "]";
    }
}
