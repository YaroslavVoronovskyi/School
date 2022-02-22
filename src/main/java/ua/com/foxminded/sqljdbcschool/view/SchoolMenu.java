package ua.com.foxminded.sqljdbcschool.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.controller.SchoolService;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class SchoolMenu {
    private SchoolService schoolService;
    
    public SchoolMenu(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    public String runSchoolMenu() throws SQLException, InputMismatchException, IOException {
        while (true) {
            try {
                schoolMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String expression = bufferedReader.readLine();
                doAction(bufferedReader, expression);
            } catch (InputMismatchException exception) {
                throw new InputMismatchException("You must choose just number from 1 to 6 do not print something else");
            }
        }
    }
    
    public void schoolMenu() {
        String menu = "1 - Find all groups with less or equals student count" + System.lineSeparator()
        + "2 - Find all students related to course with given name" + System.lineSeparator()
        + "3 - Add new student" + System.lineSeparator() 
        + "4 - Delete student by STUDENT_ID" + System.lineSeparator() 
        + "5 - Add a student to the course (from a list)" + System.lineSeparator() 
        + "6 - Remove the student from one of his or her courses" + System.lineSeparator() 
        + "Choose number from 1 to 6";
        System.out.println(menu);
    }
    
    public void doAction(BufferedReader bufferedReader, String expression) throws SQLException, IOException {
        switch (expression) {
            case "1":
                findAllGroups(bufferedReader);
                break;
            case "2":
                findAllStudents(bufferedReader);
                break;
            case "3":
                addNewStudent(bufferedReader);
                break;
            case "4":
                deleteStudentById(bufferedReader);
                break;
            case "5":
                addStudentToCourse(bufferedReader);
                break;
            case "6":
                removeStudentFromCourse(bufferedReader);
                break;
            default:
                System.out.println("Make your choose, you must choose just number from 1 to 6 do not print something else");
        }
    }
    
    public void findAllGroups(BufferedReader bufferedReader) throws SQLException, IOException {
        System.out.println("Enter required cout students in group");
        int studentId = bufferedReader.read();       
        List<Group> groupList = schoolService.searchtGroupsWithLessStudentCount(studentId);
        groupList.forEach(System.out::println);
    }
    
    public void findAllStudents(BufferedReader bufferedReader) throws SQLException, IOException {
        List<Course> courseList = schoolService.getAllCourses();
        courseList.forEach(System.out::println);
        System.out.println("Enter required coourse_ID from the list");
        int studentId = bufferedReader.read();
        List<Student> studentList = schoolService.searchStudentsByCourseId(studentId);
        studentList.forEach(System.out::println);
    }
    
    public void addNewStudent(BufferedReader bufferedReader) throws SQLException, IOException {
        System.out.println("Enter student First Name");
        String firstName = bufferedReader.readLine();
        System.out.println("Enter student Last Name");        
        String lastName = bufferedReader.readLine();
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(firstName, lastName)); 
        schoolService.addStudents(studentList);
        System.out.println("New student " + firstName + " " + lastName + " was added");
    }
    
    public void deleteStudentById(BufferedReader bufferedReader) throws SQLException, IOException {
        System.out.println("Enter student ID for delete");
        int studentId = bufferedReader.read();
        Student student = new Student(studentId);
        schoolService.deleteStudentById(student);
        System.out.println("Student with ID " + studentId + " was deleted");
    }
    
    public void addStudentToCourse(BufferedReader bufferedReader) throws SQLException, IOException {
        System.out.println("Enter the required student ID");
        int studentId = bufferedReader.read();        
        List<Course> courseList = schoolService.getAllCourses();
        courseList.forEach(System.out::println);
        System.out.println("Enter the course ID from the list");
        int courseId = bufferedReader.read(); 
        schoolService.addStudentToCourse(studentId, courseId);
        System.out.println("Student with ID " + studentId + " was add to course " + courseId);
    }
    
    public void removeStudentFromCourse(BufferedReader bufferedReader) throws SQLException, IOException {
        System.out.println("Enter the required student ID");         
        int studentId = bufferedReader.read();
        List<Course> courseList = schoolService.searchCourseByStudent(studentId);
        courseList.forEach(System.out::println);
        System.out.println("Enter the course ID from course list");     
        int courseId = bufferedReader.read();
        schoolService.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Student with ID " + studentId + " was deleted from course witn ID " + courseId);
    }
}
