package ua.com.foxminded.sqljdbcschool.controller;

import java.sql.SQLException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.controller.dao.CourseDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.GroupDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.StudentDAOImpl;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class SchoolService {    
    private CourseDAOImpl courseDAOImpl;
    private GroupDAOImpl groupDAOImpl;
    private StudentDAOImpl studentDAOImpl;
    
    public SchoolService(CourseDAOImpl courseDAOImpl, GroupDAOImpl groupDAOImpl, StudentDAOImpl studentDAOImpl) {        
        this.courseDAOImpl = courseDAOImpl;
        this.groupDAOImpl = groupDAOImpl;
        this.studentDAOImpl = studentDAOImpl;
    }
    
    public Group getGroupById(int id) throws SQLException {
        return groupDAOImpl.get(id);
    }
    
    public List<Group> getAllGroups() throws SQLException {
        return groupDAOImpl.getAll();
    }
    
    public void addGroups(List<Group> groupList) throws SQLException {
        groupDAOImpl.save(groupList);
    }
    
    public void updateGroup(Group group) throws SQLException {
        groupDAOImpl.update(group);
    }
    
    public void deleteGroupById(Group group) throws SQLException {
        groupDAOImpl.delete(group);
    }
    
    public Course getCourseById(int id) throws SQLException {
        return courseDAOImpl.get(id);
    }
    
    public List<Course> getAllCourses() throws SQLException {
        return courseDAOImpl.getAll();
    }
    
    public void addCourses(List<Course> courseList) throws SQLException {
        courseDAOImpl.save(courseList);
    }
    
    public void updateCourse(Course course) throws SQLException {
        courseDAOImpl.update(course);
    }
    
    public void deleteCourse(Course course) throws SQLException {
        courseDAOImpl.delete(course);
    }
    
    public Student getStudentById(int id) throws SQLException {
        return studentDAOImpl.get(id);
    }
    
    public List<Student> getAllStudents() throws SQLException {
        return studentDAOImpl.getAll();
    }
    
    public void addStudents(List<Student> studentList) throws SQLException {
        studentDAOImpl.save(studentList);
    }
    
    public void updateStudent(Student student) throws SQLException {
        studentDAOImpl.update(student);
    }
    
    public void deleteStudentById(Student student) throws SQLException {
        studentDAOImpl.delete(student);
    }
    
    public List<Student> searchStudentsByCourseId(int courseId) throws SQLException {
        return studentDAOImpl.searchStudentsByCourseId(courseId);
    }
    
    public void addStudentsToGroups(List<Integer> studentId, List<Integer> groupId) throws SQLException {
        studentDAOImpl.addStudentsToGroups(studentId, groupId);
    }
    
    public void addStudentToCourse(Integer studentId, Integer courseId) throws SQLException {
        courseDAOImpl.addStudentToCourse(studentId, courseId);
    }
    
    public List<Course> searchCourseByStudent(int studentId) throws SQLException {
        return courseDAOImpl.searchCourseByStudent(studentId);
    }
    
    public List<Group> searchtGroupsWithLessStudentCount(int studentId) throws SQLException {
        return groupDAOImpl.searchtGroupsWithLessStudentCount(studentId);
    }
    
    public void deleteStudentFromCourse(Integer studentId, Integer courseId) throws SQLException {
        courseDAOImpl.deleteStudentFromCourse(studentId, courseId);
    }
}
