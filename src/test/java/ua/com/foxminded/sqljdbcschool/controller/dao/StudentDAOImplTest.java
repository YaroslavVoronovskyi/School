package ua.com.foxminded.sqljdbcschool.controller.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.connection.DataScriptExecution;
import ua.com.foxminded.sqljdbcschool.controller.DataGenerator;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class StudentDAOImplTest {
    private static final String CREATE_TABLES = "ScriptSchool.sql";
    private static final String CREATE_GROUPS = "ScriptInsertGroups.sql";
    private static final String CREATE_STUDENTS = "ScriptInsertStudents.sql";
    private static final String CREATE_COURSES = "ScriptInsertCourses.sql";
    
    DataScriptExecution dataScriptExecution = new DataScriptExecution();
    DataGenerator dataGenerator = new DataGenerator();
    StudentDAOImpl studentDAOImpl = new StudentDAOImpl();
    CourseDAOImpl courseDAOImpl = new CourseDAOImpl();
    GroupDAOImpl groupDAOImpl = new GroupDAOImpl();
    
    @BeforeEach
    public void runScriptShouldCreateAndInitTables() throws SQLException, ClassNotFoundException, IOException {
        dataScriptExecution.scriptRunner(CREATE_TABLES);
        dataScriptExecution.scriptRunner(CREATE_STUDENTS);
        dataScriptExecution.scriptRunner(CREATE_COURSES);
        dataScriptExecution.scriptRunner(CREATE_GROUPS);
    }
    
    @Test
    public void shouldReturnExpectedNumberOfGroups() throws SQLException {
        List<Student> studentList = studentDAOImpl.getAll();
        int expected = 10;
        int actual = studentList.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void shouldReturnExpectedIdOfStudents() throws SQLException {
        Student expectedStudent = new Student(1);
        expectedStudent.setFirstName("Viktor");
        expectedStudent.setLastName("Tsygankov");
        Student actualStudent = studentDAOImpl.get(1); 
        assertEquals(expectedStudent.getId(), actualStudent.getId());
        assertEquals(expectedStudent.getFirstName(), actualStudent.getFirstName());
        assertEquals(expectedStudent.getLastName(), actualStudent.getLastName());
    }
    
    @Test
    public void shouldDeleteExpectedIdOfStudents() throws SQLException {
        Student expectedStudent = new Student(1);
        Student actualStudent = new Student(1);
        studentDAOImpl.delete(actualStudent);
        assertEquals(expectedStudent.getId(), actualStudent.getId());
        assertEquals(expectedStudent.getGroupId(), actualStudent.getGroupId());
        assertEquals(expectedStudent.getFirstName(), actualStudent.getFirstName());
        assertEquals(expectedStudent.getLastName(), actualStudent.getLastName());
    }
    
    @Test
    public void shouldInitTableStudents() throws SQLException {
        Student studentEleven = new Student (11);
        studentEleven.setFirstName("Yaroslav");
        studentEleven.setLastName("Voronovskyi");
        List<Student> expectedStudentList = new ArrayList<>();
        expectedStudentList.add(studentEleven);
        studentDAOImpl.save(expectedStudentList);    
        assertNotNull(studentDAOImpl.get(11));
        assertTrue(studentDAOImpl.getAll().contains(studentEleven));
    }
    
    @Test
    public void shouldUpdateStudent() throws SQLException {
        int studentId = 1;
        Student expectedStudent = new Student(studentId);
        expectedStudent.setFirstName("Yaroslav");
        expectedStudent.setLastName("Voronovskyi");
        studentDAOImpl.update(expectedStudent);
        Student actualStudent = studentDAOImpl.get(studentId);
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }
    
    @Test
    public void shouldAddStudentToGroup() throws SQLException, ClassNotFoundException, IOException {
        Student student = studentDAOImpl.get(1);
        Group group = groupDAOImpl.get(1);
        List<Integer> studentId = new ArrayList<>();
        studentId.add(student.getId());
        List<Integer> groupId = new ArrayList<>();
        groupId.add(group.getId());
        studentDAOImpl.addStudentsToGroups(studentId, groupId);
        assertTrue(studentId.add(student.getId()));
        assertTrue(studentId.add(student.getGroupId()));
    }
    
    @Test
    public void shouldSearchStudentsByCourseId() throws SQLException {
        Course course = courseDAOImpl.get(1);
        Student student = studentDAOImpl.get(1);
        courseDAOImpl.addStudentToCourse(student.getId(), course.getId());
        assertNotNull(studentDAOImpl.searchStudentsByCourseId(course.getId()).add(student));
        assertTrue(studentDAOImpl.searchStudentsByCourseId(course.getId()).add(student));
 
    }
    
    @Test
    public void saveShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Student student = new Student();        
        List<Student> studentList = new ArrayList<>();
        student.setId(1);
        studentList.add(student);
        assertThrows(SQLException.class, () -> studentDAOImpl.save(studentList));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectInputData() throws SQLException {
        Student student = studentDAOImpl.get(1);
        student.setFirstName(null);
        student.setLastName(null);
        student.setId(5);
        assertThrows(SQLException.class, () -> studentDAOImpl.update(student));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectZeroInputData() throws SQLException {
        Student student = studentDAOImpl.get(1);
        student.setFirstName(null);
        student.setLastName(null);
        student.setId(0);
        assertThrows(SQLException.class, () -> studentDAOImpl.update(student));
    }
    
    @Test
    public void addStudentToCourseShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        List<Integer> studentId = Arrays.asList(10); 
        List<Integer> groupId = Arrays.asList(100);
        assertThrows(SQLException.class, () -> studentDAOImpl.addStudentsToGroups(studentId, groupId));
    }
    
    @Test
    public void deleteShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {       
        Student student = new Student(-1);
        assertThrows(SQLException.class, () -> studentDAOImpl.delete(student));
    }
    
    @Test
    public void deleteShouldThrowSQLExceptionThroughZeroInputData() throws SQLException {       
        Student student = new Student();
        assertThrows(SQLException.class, () -> studentDAOImpl.delete(student));
    }
    
    @Test
    public void getShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        assertThrows(SQLException.class, () -> studentDAOImpl.get(-1));
    }
    
    @Test
    public void searchStudentsByCourseIdShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Course course = new Course(-1);
        assertThrows(SQLException.class, () -> studentDAOImpl.searchStudentsByCourseId(course.getId()));
    }
    
    @Test
    public void getAllShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Student student1 = studentDAOImpl.get(1);
        studentDAOImpl.delete(student1);
        Student student2 = studentDAOImpl.get(2);
        studentDAOImpl.delete(student2);
        Student student3 = studentDAOImpl.get(3);
        studentDAOImpl.delete(student3);
        Student student4 = studentDAOImpl.get(4);
        studentDAOImpl.delete(student4);
        Student student5 = studentDAOImpl.get(5);
        studentDAOImpl.delete(student5);
        Student student6 = studentDAOImpl.get(6);
        studentDAOImpl.delete(student6);
        Student student7 = studentDAOImpl.get(7);
        studentDAOImpl.delete(student7);
        Student student8 = studentDAOImpl.get(8);
        studentDAOImpl.delete(student8);
        Student student9 = studentDAOImpl.get(9);
        studentDAOImpl.delete(student9);
        Student student10 = studentDAOImpl.get(10);
        studentDAOImpl.delete(student10);
        assertThrows(SQLException.class, () -> studentDAOImpl.getAll());
    }
}
