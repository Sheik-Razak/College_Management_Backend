package dao;

import model.Exam;
import model.Faculty;
import model.Subject;

import java.util.List;
import java.util.Scanner;
import custom_exception.DatabaseConnectionException;
import custom_exception.DuplicateEntryException;

public class ExamMenuHandler {
    private TimetableDAO timetableDao;
    private ExamDAO examDao;
    private Faculty faculty;
    private Scanner sc;

    public ExamMenuHandler(TimetableDAO timetableDao, ExamDAO examDao, Faculty faculty, Scanner sc) {
        this.timetableDao = timetableDao;
        this.examDao = examDao;
        this.faculty = faculty;
        this.sc = sc;
    }

    public void assignExam() {
        List<Subject> subjectList = null;
        try {
            subjectList = timetableDao.getSubjectsForFacultyInTimetable(faculty.getFacultyId());
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        if(subjectList.isEmpty()) {
            System.out.println("No subjects assigned in your timetable.");
            return;
        }
        System.out.println("\nSelect Subject to Assign Exam:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-40s %-20s%n", "No.", "Subject Name", "Subject ID");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < subjectList.size(); i++) {
            System.out.printf("%-5d %-40s %-20d%n", i + 1, subjectList.get(i).getSubjectName(), subjectList.get(i).getSubjectId());
        }

        try {
            System.out.print("Enter option(No.): ");
            int subjectOption = Integer.parseInt(sc.nextLine());
            if(subjectOption < 1 || subjectOption > subjectList.size()) {
                System.out.println("Invalid option.");
                return;
            }
            Subject selectedSubject = subjectList.get(subjectOption-1);
            int subjectId = selectedSubject.getSubjectId();
            int courseId = selectedSubject.getCourseId();
            System.out.print("Enter Exam Date (YYYY-MM-DD): ");
            String examDate = sc.nextLine();
            System.out.print("Enter Exam Type (Assessment/Midterm/Final): ");
            String examType = sc.nextLine();
            System.out.print("Enter Total Marks: ");
            int totalMarks = Integer.parseInt(sc.nextLine());
            Exam exam = new Exam(courseId, subjectId, examDate, examType, totalMarks);

            boolean inserted;
            try {
                inserted = examDao.insertExam(exam);
                System.out.println(inserted ? "Exam assigned!" : "Failed to assign exam.");
            } catch (DuplicateEntryException e) {
                System.out.println(" " + e.getMessage());
            } catch (DatabaseConnectionException e) {
                System.out.println("DB Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    public void deleteExam() {
        List<Subject> subjectList = null;
        try {
            subjectList = timetableDao.getSubjectsForFacultyInTimetable(faculty.getFacultyId());
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
        if(subjectList.isEmpty()) {
            System.out.println("No subjects assigned in your timetable.");
            return;
        }
        System.out.println("Select subject to delete exam:");
        for(int i=0; i<subjectList.size(); i++) {
            System.out.printf("%d. %s (ID: %d)\n", i+1, subjectList.get(i).getSubjectName(), subjectList.get(i).getSubjectId());
        }
        try {
            System.out.print("Enter option: ");
            int subjectOption = Integer.parseInt(sc.nextLine());
            if(subjectOption < 1 || subjectOption > subjectList.size()) {
                System.out.println("Invalid option.");
                return;
            }
            Subject selectedSubject = subjectList.get(subjectOption-1);
            int subject_Id = selectedSubject.getSubjectId();
            int courseId = selectedSubject.getCourseId();

            List<Exam> examList;
            try {
                examList = examDao.getExamsByCourseAndSubject(courseId, subject_Id);
            } catch (DatabaseConnectionException e) {
                System.out.println("DB Error: " + e.getMessage());
                return;
            }
            if(examList.isEmpty()) {
                System.out.println("No exams assigned for this subject.");
                return;
            }

            System.out.println("Select exam to delete:");
            for(int i=0; i<examList.size(); i++) {
                Exam exam = examList.get(i);
                System.out.printf("%d. %s on %s (ID: %d) [Total Marks: %d]\n", i+1, exam.getExamType(), exam.getExamDate(), exam.getExamId(), exam.getTotalMarks());
            }
            System.out.print("Enter option: ");

            int examOption = Integer.parseInt(sc.nextLine());
            if(examOption < 1 || examOption > examList.size()) {
                System.out.println("Invalid option.");
                return;
            }

            Exam examToDelete = examList.get(examOption-1);
            try {
                boolean deleted = examDao.deleteExam(examToDelete.getExamId());
                System.out.println(deleted ? "Exam deleted successfully!" : "Failed to delete exam.");
            } catch (DatabaseConnectionException e) {
                System.out.println("DB Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }
}
