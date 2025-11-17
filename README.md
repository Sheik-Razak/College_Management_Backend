# College Management System

A comprehensive, modular Java backend application for managing college workflows and records, built with **Maven**, robust **JUnit testing**, and **custom exceptions** for advanced error handling.

## Overview

This system automates essential college activities—admission, academic records, staff & student management, attendance, exams, and results. Designed with scalability, maintainability, and real-world accuracy using JDBC and role-based menus for Admin, Faculty, and Student.

## Features

- **Role-Based Access Control**  
  Secure user authentication and dynamic menus for Admin, Faculty, and Student roles.
- **Student Management**  
  Add, update, view, and delete student profiles, courses, contact info, and results.
- **Faculty Management**  
  Manage faculty records, subject assignments, and department roles.
- **Course & Subject Management**  
  CRUD for courses, subjects, and department mappings; flexible academic structures.
- **Attendance Management**  
  Attendance entry and retrieval features for staff and students.
- **Exam & Results**  
  Exam scheduling, automatic grading, custom 100-point scale; results publishing.
- **Timetable Scheduling**  
  Create, update, and view timetables for courses and staff.
- **Custom Exceptions**  
  Implements domain-specific custom exceptions for error scenarios (e.g., `StudentNotFoundException`, `AttendanceNotFoundException`, `DuplicateEntryException`, `DatabaseConnectionException`, `UserNotFoundException`, `DepartmentNotFoundException`). This ensures clear separation and handling of database, logic, and input errors.[1][2][3]

## Technology Stack

| Layer              | Technology                          | Description                                 |
|--------------------|-------------------------------------|---------------------------------------------|
| **Language**       | Java                                | OOP, modularized entities/models            |
| **Build Tool**     | Maven                               | Dependency and lifecycle management         |
| **Database**       | SQL (MySQL recommended)             | Relational table design, normalization      |
| **Access**         | JDBC + DAO                          | Logic/data access separation                |
| **Testing**        | JUnit                               | Automated unit testing                      |
| **IDE**            | IntelliJ IDEA                       | Coding and debugging                        |
| **Exceptions**     | Custom Java Exception Classes       | Fine-grained error handling                 |

## Custom Exceptions

Specialized exception classes handle all major error scenarios:
- **DatabaseConnectionException** - For DB connection/operation issues.
- **StudentNotFoundException, FacultyNotFoundException** - Entity lookup failures.
- **AttendanceNotFoundException, TimetableNotFoundException, SubjectNotFoundException, DepartmentNotFoundException** - Related to missing records.
- **DuplicateEntryException** - Violation of unique constraints.
- **UserNotFoundException** - Invalid login/signup requests.

This architecture makes error handling clear for users and developers, improves debugging, and aligns with enterprise coding standards.[2][3][1]

## Build & Setup

1. **Clone the repository**.
2. **Configure DB connection** in `DBUtil.java`.
3. **Build and test** with Maven:
   ```bash
   mvn clean install        # Build all modules
   mvn test                 # Run JUnit tests
   ```
4. **Import into IntelliJ IDEA.** Launch using `Main.java` or Maven plugin.

## Modular Structure

- Separate model and DAO for each entity: Student, Faculty, Course, Subject, Department, Attendance, Exam, Result.
- Role-based menu classes for command execution.
- DAO pattern isolates persistence logic.
- Exception handling layered throughout for robust operations.

## JUnit Testing

Unit tests are provided for backend logic/DAO modules to guarantee correct CRUD operations, business rules, and error handling. All tests integrate with the Maven lifecycle for automation.

## Maven Integration

Project uses a `pom.xml` for dependency, build, and plugin management (including JUnit). Simply import as a Maven project—no manual classpath setup!

## Getting Started

- Import into your IDE as a Maven project.
- Configure the database connection as needed.
- Build and run as shown above.
- Optionally deploy/extend the system for web or desktop presentation.

## Contributing

Pull requests are welcome! Contributions should adhere to code/modularity standards, use custom exceptions, and provide supporting unit tests.
