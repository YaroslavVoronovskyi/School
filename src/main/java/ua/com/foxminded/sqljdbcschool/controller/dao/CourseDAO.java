package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.SQLException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.model.Course;

public interface CourseDAO { 
    public Course get(int id) throws SQLException;
    
    public List<Course> getAll() throws SQLException;
    
    public void save(List<Course> courseList) throws SQLException;
    
    public void update(Course course) throws SQLException;
    
    public void delete(Course course) throws SQLException;

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) throws SQLException;

    public void addStudentToCourse(Integer studentId, Integer courseId) throws SQLException;

    public List<Course> searchCourseByStudent(int studentId) throws SQLException;
}
