package model;

public class Timetable {
    private int timetableId;
    private int courseId;
    private int subjectId;
    private int facultyId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String roomNo;

    public Timetable() {}

    public Timetable(int timetableId, int courseId, int subjectId, int facultyId,
                     String dayOfWeek, String startTime, String endTime, String roomNo) {
        this.timetableId = timetableId;
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNo = roomNo;
    }

    public int getTimetableId() { return timetableId; }
    public void setTimetableId(int timetableId) { this.timetableId = timetableId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    @Override
    public String toString() {
        return "Timetable [timetableId=" + timetableId +
                ", courseId=" + courseId +
                ", subjectId=" + subjectId +
                ", facultyId=" + facultyId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", roomNo=" + roomNo + "]";
    }
}
