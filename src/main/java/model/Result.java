package model;

public class Result {
    private int resultId;
    private int studentId;
    private int examId;
    private int marksObtained;
    private String grade;

    public Result() {}

    public Result(int resultId, int studentId, int examId, int marksObtained, String grade) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.examId = examId;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }
    public Result(int studentId, int examId, int marksObtained, String grade) {
        this.studentId = studentId;
        this.examId = examId;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Result [resultId=" + resultId +
                ", studentId=" + studentId +
                ", examId=" + examId +
                ", marksObtained=" + marksObtained +
                ", grade=" + grade + "]";
    }
}
