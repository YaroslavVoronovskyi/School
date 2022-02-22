package ua.com.foxminded.sqljdbcschool.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ua.com.foxminded.sqljdbcschool.controller.SchoolService;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class SchoolMenuTest {
    private SchoolMenu schoolMenu;
    
    @Test
    public void chekFindAllGroupsShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        String expression = "1";       
        schoolMenu.doAction(bufferedReader, expression);
        schoolServiceMock.searchtGroupsWithLessStudentCount(1);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).searchtGroupsWithLessStudentCount(captor.capture());
        assertEquals(1, captor.getValue());       
    }
    
    @Test
    public void chekFindAllStudentsShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        String expression = "2";      
        schoolMenu.doAction(bufferedReader, expression);
        Mockito.when(schoolServiceMock.getAllCourses()).thenReturn(createFakeCourseList());     
        Mockito.verify(schoolServiceMock, Mockito.times(1)).getAllCourses();
        schoolServiceMock.searchStudentsByCourseId(1);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).searchStudentsByCourseId(captor.capture());
        assertEquals(1, captor.getValue());
    }
    
    @Test
    public void chekAddNewStudentShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        String expression = "3";
        schoolMenu.doAction(bufferedReader, expression);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        List<Student> studentList = new ArrayList<>();
        Student student = createFakeStudent();
        studentList.add(student);
        schoolServiceMock.addStudents(studentList);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).addStudents(captor.capture());
        assertTrue(captor.getValue().contains(student));
    }
    
    
    @Test
    public void chekDeleteStudentByIdShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        String expression = "4";
        schoolMenu.doAction(bufferedReader, expression);
        Student student = createFakeStudent();
        schoolServiceMock.deleteStudentById(student);         
        Mockito.verify(schoolServiceMock, Mockito.times(2)).deleteStudentById(captor.capture());
        assertTrue(captor.getValue().equals(student));
    }
   
    @Test
    public void chekAddStudentToCourseShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        ArgumentCaptor<Integer> captorOne = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> captorTwo = ArgumentCaptor.forClass(Integer.class);
        String expression = "5";
        schoolMenu.doAction(bufferedReader, expression);
        Mockito.when(schoolServiceMock.getAllCourses()).thenReturn(createFakeCourseList());     
        Mockito.verify(schoolServiceMock, Mockito.times(1)).getAllCourses();
        schoolServiceMock.addStudentToCourse(2, 1);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).addStudentToCourse(captorOne.capture(), captorTwo.capture());
        assertEquals(2, captorOne.getValue());
        assertEquals(1, captorTwo.getValue());
    }
    
    @Test
    public void chekRemoveStudentFromCourseShouldShowThatRequiredMethodWasCalled() throws IOException, InputMismatchException, SQLException {
        SchoolService schoolServiceMock = mock(SchoolService.class);
        schoolMenu = new SchoolMenu(schoolServiceMock); 
        BufferedReader bufferedReader = mock(BufferedReader.class);
        ArgumentCaptor<Integer> captorOne = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> captorTwo = ArgumentCaptor.forClass(Integer.class);
        String expression = "6";
        schoolMenu.doAction(bufferedReader, expression);
        schoolServiceMock.searchCourseByStudent(15);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).searchCourseByStudent(captorOne.capture());
        schoolServiceMock.deleteStudentFromCourse(15, 1);
        Mockito.verify(schoolServiceMock, Mockito.times(2)).deleteStudentFromCourse(captorOne.capture(), captorTwo.capture());
        assertEquals(15, captorOne.getValue());
        assertEquals(1, captorTwo.getValue());
    }
    
    private Student createFakeStudent() {
        Student student = new Student(1);
        student.setFirstName("Viktor");
        student.setLastName("Tsygankov");
        return student;
    }
    
    private List<Course> createFakeCourseList() {
        List<Course> courseList = new ArrayList<>();
        Course courseOne = new Course(1, "Math");
        courseOne.setDescription("Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis");
        Course courseTwo = new Course(2, "Physics");
        courseTwo.setDescription("Physics is the natural science that studies matter, its motion and behavior through space and time, and the related entities of energy and force.");
        Course courseThree = new Course(3, "Chemistry");
        courseThree.setDescription("Chemistry is the scientific discipline involved with elements and compounds composed of atoms, molecules and ions: their composition, structure, properties, behavior and the changes they undergo during a reaction with other substances.");
        courseList.add(courseOne);
        courseList.add(courseTwo);
        courseList.add(courseThree);
        return courseList;
    }
}
