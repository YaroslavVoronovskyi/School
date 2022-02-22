package ua.com.foxminded.sqljdbcschool.controller.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.connection.DataScriptExecution;
import ua.com.foxminded.sqljdbcschool.controller.DataGenerator;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class CourseDAOImplTest {
    private static final String CREATE_TABLES = "ScriptSchool.sql";
    private static final String CREATE_COURSES = "ScriptInsertCourses.sql";
    private static final String CREATE_STUDENTS = "ScriptInsertStudents.sql";
    private static final int FIRST_COLUMN_INDEX = 1;

    DataScriptExecution dataScriptExecution = new DataScriptExecution();
    DataGenerator dataGenerator = new DataGenerator();
    CourseDAOImpl courseDAOImpl = new CourseDAOImpl();
    StudentDAOImpl studentDAOImpl = new StudentDAOImpl();
    
    @BeforeEach
    public void runScriptShouldCreateAndInsertTables() throws SQLException, ClassNotFoundException, IOException {
        dataScriptExecution.scriptRunner(CREATE_TABLES);
        dataScriptExecution.scriptRunner(CREATE_COURSES);
        dataScriptExecution.scriptRunner(CREATE_STUDENTS);
    }
    
    @Test()
    public void shouldReturnExpectedNumberOfCourses() throws SQLException {
        List<Course> courseList = courseDAOImpl.getAll();
        int expected = 10;
        int actual = courseList.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void shouldReturnExpectedIdOfCourses() throws SQLException {
        int courseId = 1;
        Course expectedCourse = new Course(FIRST_COLUMN_INDEX, "Math");
        expectedCourse.setDescription("Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis");
        Course actualCourse = courseDAOImpl.get(courseId); 
        assertEquals(expectedCourse.getId(), actualCourse.getId());
        assertEquals(expectedCourse.getName(), actualCourse.getName());
        assertEquals(expectedCourse.getDescription(), actualCourse.getDescription());
    }
    
    @Test
    public void shouldDeleteExpectedIdOfCourses() throws SQLException {
        Course expectedCourse = new Course(FIRST_COLUMN_INDEX);
        Course actualCourse = new Course(FIRST_COLUMN_INDEX);
        courseDAOImpl.delete(actualCourse);
        assertEquals(expectedCourse.getId(), actualCourse.getId());
        assertEquals(expectedCourse.getName(), actualCourse.getName());
        assertEquals(expectedCourse.getDescription(), actualCourse.getDescription());
    }
    
    @Test
    public void shouldInitTableCourses() throws SQLException {
        Course courseEleven = new Course(11, "MATH");
        courseEleven.setDescription("Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis");
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(courseEleven);
        courseDAOImpl.save(expectedCourseList);    
        assertNotNull(courseDAOImpl.get(11));
        assertTrue(courseDAOImpl.getAll().contains(courseEleven));
    }
    
    @Test
    public void shouldUpdateCourse() throws SQLException {
        int courseId = 1;
        Course expectedCourse = new Course(courseId, "Math - Math");
        expectedCourse.setDescription("Math - Math");
        courseDAOImpl.update(expectedCourse);
        Course actualCourse = courseDAOImpl.get(courseId);
        assertNotNull(actualCourse);
        assertEquals(expectedCourse, actualCourse);
    }
    
    @Test
    public void shouldAddStudentToCourse() throws SQLException, ClassNotFoundException, IOException {
        Course course = courseDAOImpl.get(1);
        Student student = studentDAOImpl.get(1);
        courseDAOImpl.addStudentToCourse(student.getId(), course.getId());
        assertNotNull(courseDAOImpl.searchCourseByStudent(student.getId()).contains(courseDAOImpl.get(1)));
        assertTrue(courseDAOImpl.searchCourseByStudent(student.getId()).contains(courseDAOImpl.get(1)));
    }
    
    @Test
    public void shoulDeleteStudentFromCourse() throws SQLException, ClassNotFoundException, IOException {
        Course course = courseDAOImpl.get(1);
        Student student = studentDAOImpl.get(1);
        courseDAOImpl.addStudentToCourse(student.getId(), course.getId());
        courseDAOImpl.deleteStudentFromCourse(student.getId(), course.getId());
        assertNotNull(studentDAOImpl.searchStudentsByCourseId(course.getId()).contains(studentDAOImpl.get(1)));
        assertFalse(studentDAOImpl.searchStudentsByCourseId(course.getId()).contains(studentDAOImpl.get(1)));
    }
    
    @Test
    public void shouldSearchCourseByStudent() throws SQLException {
        Course course = courseDAOImpl.get(1);
        Student student = studentDAOImpl.get(1);
        courseDAOImpl.addStudentToCourse(student.getId(), course.getId());
        assertNotNull(courseDAOImpl.searchCourseByStudent(student.getId()));
        assertTrue(courseDAOImpl.searchCourseByStudent(student.getId()).contains(courseDAOImpl.get(1)));
    }
    
    @Test
    public void saveShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Course course = new Course();        
        List<Course> courseList = new ArrayList<>();
        course.setId(1);
        courseList.add(course);
        assertThrows(SQLException.class, () -> courseDAOImpl.save(courseList));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectInputData() throws SQLException {
        Course course = courseDAOImpl.get(1);      
        course.setId(2);    
        assertThrows(SQLException.class, () -> courseDAOImpl.update(course));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectZeroInputData() throws SQLException {
        Course course = courseDAOImpl.get(1);      
        course.setId(0);    
        assertThrows(SQLException.class, () -> courseDAOImpl.update(course));
    }
    
    @Test
    public void addStudentToCourseShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Integer studentId = 1;
        Integer courseId = 12;
        assertThrows(SQLException.class, () -> courseDAOImpl.addStudentToCourse(studentId, courseId));
    }
    
    @Test
    public void addStudentToCourseShouldThrowSQLExceptionThroughIncorrectZeroInputData() throws SQLException {
        Integer studentId = 0;
        Integer courseId = 0;
        assertThrows(SQLException.class, () -> courseDAOImpl.addStudentToCourse(studentId, courseId));
    }
    
    @Test
    public void deleteStudentFromCourseShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Integer studentId = -1;
        Integer courseId = -1;       
        assertThrows(SQLException.class, () -> courseDAOImpl.deleteStudentFromCourse(studentId, courseId));
    }
    
    @Test
    public void deleteShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {       
        Course course = new Course(-1);   
        assertThrows(SQLException.class, () -> courseDAOImpl.delete(course));
    }
    
    @Test
    public void getShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        assertThrows(SQLException.class, () -> courseDAOImpl.get(-1));
    }
    
    @Test
    public void searchCourseByStudentShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Student student = new Student(-1);
        assertThrows(SQLException.class, () -> courseDAOImpl.searchCourseByStudent(student.getId()));
    }
    
    @Test
    public void getAllShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Course course1 = courseDAOImpl.get(1);
        courseDAOImpl.delete(course1);
        Course course2 = courseDAOImpl.get(2);
        courseDAOImpl.delete(course2);
        Course course3 = courseDAOImpl.get(3);
        courseDAOImpl.delete(course3);
        Course course4 = courseDAOImpl.get(4);
        courseDAOImpl.delete(course4);
        Course course5 = courseDAOImpl.get(5);
        courseDAOImpl.delete(course5);
        Course course6 = courseDAOImpl.get(6);
        courseDAOImpl.delete(course6);
        Course course7 = courseDAOImpl.get(7);
        courseDAOImpl.delete(course7);
        Course course8 = courseDAOImpl.get(8);
        courseDAOImpl.delete(course8);
        Course course9 = courseDAOImpl.get(9);
        courseDAOImpl.delete(course9);
        Course course10 = courseDAOImpl.get(10);
        courseDAOImpl.delete(course10);
        assertThrows(SQLException.class, () -> courseDAOImpl.getAll());
    }
}
