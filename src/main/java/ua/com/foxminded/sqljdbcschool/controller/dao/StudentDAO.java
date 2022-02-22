package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.SQLException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.model.Student;

public interface StudentDAO {
    public Student get(int id) throws SQLException;
    
    public List<Student> getAll() throws SQLException;
    
    public void save(List<Student> studentList) throws SQLException;
    
    public void update(Student student) throws SQLException;
    
    public void delete(Student student) throws SQLException;

    public List<Student> searchStudentsByCourseId(int courseId) throws SQLException;

    public void addStudentsToGroups(List<Integer> studentId, List<Integer> groupId) throws SQLException;
}
