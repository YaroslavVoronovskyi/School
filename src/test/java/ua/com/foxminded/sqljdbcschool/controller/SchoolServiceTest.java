package ua.com.foxminded.sqljdbcschool.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ua.com.foxminded.sqljdbcschool.controller.dao.CourseDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.GroupDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.StudentDAOImpl;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class SchoolServiceTest {     
    private CourseDAOImpl courseMock;
    private GroupDAOImpl groupMock;     
    private StudentDAOImpl studentMock;
    private SchoolService schoolService;
    
    @Test
    public void checkMethodGet() throws SQLException {
        initMocksForOneObject();
        assertEquals(schoolService.getCourseById(1), createFakeCourse());
        assertEquals(schoolService.getGroupById(1), createFakeGroup());
        assertEquals(schoolService.getStudentById(1), createFakeStudent());   
    }  
    
    @Test
    public void checkMethodGetAll() throws SQLException {
        initMocksForManyObjects();
        assertEquals(schoolService.getAllCourses(), createFakeCourseList());
        assertEquals(schoolService.getAllGroups(), createFakeGroupList());
        assertEquals(schoolService.getAllStudents(), createFakeStudentList());   
    }  
    
    @Test
    public void checkMethodDelete() throws SQLException {
        initMocksForManyObjects();
        Course course = new Course(1);
        schoolService.deleteCourse(course);
        Group group = new Group(1);
        schoolService.deleteGroupById(group);
        Student student = new Student(1);
        schoolService.deleteStudentById(student);
        assertFalse(schoolService.getAllCourses().contains(course));
        assertFalse(schoolService.getAllGroups().contains(group));
        assertFalse(schoolService.getAllStudents().contains(student));
    } 
    
    @Test
    public void checkMethodUpdate() throws SQLException {
        initMocksForOneObject();
        Course course = schoolService.getCourseById(1);
        course.setName("Java");
        course.setDescription("Programming language.");
        schoolService.updateCourse(course);
        Group group = schoolService.getGroupById(1);
        group.setName("FF-22");
        schoolService.updateGroup(group);
        Student student = schoolService.getStudentById(1);
        student.setFirstName("Yaroslav");
        student.setLastName("Voronovskyi");
        schoolService.updateStudent(student);     
        assertEquals(courseMock.getAll(), schoolService.getAllCourses());
        assertEquals(groupMock.getAll(), schoolService.getAllGroups());
        assertEquals(studentMock.getAll(), schoolService.getAllStudents());
    } 
    
    @Test
    public void checkMethodSave() throws SQLException {
        initMocksForManyObjects();
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Programming language.");
        List<Course> courseList = schoolService.getAllCourses();
        courseList.add(course);
        schoolService.addCourses(courseList);
        Group group = new Group();
        group.setName("FF-22");
        List<Group> groupList = schoolService.getAllGroups();
        groupList.add(group);
        schoolService.addGroups(groupList);
        Student student = new Student();
        student.setFirstName("Yaroslav");
        student.setLastName("Voronovskyi");
        List<Student> studentList = schoolService.getAllStudents();
        studentList.add(student);
        schoolService.addStudents(studentList);   
        assertTrue(schoolService.getAllCourses().contains(course));
        assertTrue(schoolService.getAllGroups().contains(group));
        assertTrue(schoolService.getAllStudents().contains(student));
    }
    
    @Test
    public void checkMethodAddStudentToCourse() throws SQLException {
        initMocksForOneObject();
        Course course = schoolService.getCourseById(1);
        Student student = schoolService.getStudentById(1);
        schoolService.addStudentToCourse(student.getId(), course.getId());
        assertEquals(schoolService.searchCourseByStudent(student.getId()), schoolService.searchStudentsByCourseId(course.getId()));
    }
    
    @Test
    public void checkMethodAddStudentToGroup() throws SQLException {
        initMocksForOneObject();
        Group group = schoolService.getGroupById(1);
        Student student = schoolService.getStudentById(1);
        List<Integer> studentId = new ArrayList<>();
        studentId.add(student.getId());
        List<Integer> groupId = new ArrayList<>();
        groupId.add(group.getId());
        schoolService.addStudentsToGroups(studentId, groupId);
        assertTrue(schoolService.searchtGroupsWithLessStudentCount(student.getId()).add(group));
    }
    
    @Test
    public void checkMethodDeleteStudentFromCourse() throws SQLException {
        initMocksForOneObject();
        Course course = schoolService.getCourseById(1);
        Student student = schoolService.getStudentById(1);
        schoolService.addStudentToCourse(student.getId(), course.getId());
        schoolService.deleteStudentFromCourse(student.getId(), course.getId());
        assertEquals(schoolService.searchCourseByStudent(student.getId()), schoolService.searchStudentsByCourseId(course.getId()));
    }
    
    private void initMocksForOneObject() throws SQLException {
        courseMock = mock(CourseDAOImpl.class);
        groupMock = mock(GroupDAOImpl.class);       
        studentMock = mock(StudentDAOImpl.class);        
        Mockito.when(courseMock.get(1)).thenReturn(createFakeCourse());
        Mockito.when(groupMock.get(1)).thenReturn(createFakeGroup());
        Mockito.when(studentMock.get(1)).thenReturn(createFakeStudent());
        schoolService = new SchoolService(courseMock, groupMock, studentMock);
    }
    
    private void initMocksForManyObjects() throws SQLException {
        courseMock = mock(CourseDAOImpl.class);
        groupMock = mock(GroupDAOImpl.class);       
        studentMock = mock(StudentDAOImpl.class);        
        Mockito.when(courseMock.getAll()).thenReturn(createFakeCourseList());
        Mockito.when(groupMock.getAll()).thenReturn(createFakeGroupList());
        Mockito.when(studentMock.getAll()).thenReturn(createFakeStudentList());
        schoolService = new SchoolService(courseMock, groupMock, studentMock);
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
    
    private List<Group> createFakeGroupList() {
        List<Group> groupsList = new ArrayList<>();
        Group groupOne = new Group(1, "QW-11");
        Group groupTwo = new Group(2, "ER-12");
        Group groupThree = new Group(3, "TY-21");
        groupsList.add(groupOne);
        groupsList.add(groupTwo);
        groupsList.add(groupThree);
        return groupsList;
    }

    private List<Student> createFakeStudentList() {
        List<Student> studentsList = new ArrayList<>();
        Student studentOne = new Student(1);
        studentOne.setFirstName("Viktor");
        studentOne.setLastName("Tsygankov");
        Student studentTwo = new Student(2);
        studentTwo.setFirstName("Denys");
        studentTwo.setLastName("Tsygankov");
        Student studentThree = new Student(3);
        studentThree.setFirstName("Olena");
        studentThree.setLastName("Tsygankov");
        studentsList.add(studentOne);
        studentsList.add(studentTwo);
        studentsList.add(studentThree);
        return studentsList;
    }

    private Course createFakeCourse() {
        Course course = new Course(1, "Math");
        course.setDescription("Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis");
        return course;
    }

    private Group createFakeGroup() {
        Group group = new Group(1, "QW-11");
        return group;
    }

    private Student createFakeStudent() {
        Student student = new Student(1);
        student.setFirstName("Viktor");
        student.setLastName("Tsygankov");
        return student;
    }
}
