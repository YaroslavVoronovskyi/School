package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.connection.DataSource;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class StudentDAOImpl implements StudentDAO {
    private static final String GET_QUERY = "SELECT * FROM students WHERE student_id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM students";
    private static final String INSERT_QUERY = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM students WHERE student_id = ?";
    private static final String GET_STUDENTS_BY_COURSE_QUERY = "SELECT students.first_name, students.last_name "
            + "FROM students LEFT JOIN students_courses ON students.student_id = students_courses.student_ref "
            + "WHERE (students_courses.course_ref) = ?";
    private static final String ADD_STUDENTS_TO_GROUPS_QUERY = "UPDATE students SET group_ref=? WHERE student_id=?";
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int SECOND_COLUMN_INDEX = 2;
    private static final int THIRD_COLUMN_INDEX = 3;
    private static final int FOURTH_COLUMN_INDEX = 4;
    
    @Override
    public Student get(int id) throws SQLException {
        Student student = null;
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
                if (id <= 0) {
                    throw new SQLException("Student ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    student = new Student();
                    student.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    student.setGroupId(resultSet.getInt(SECOND_COLUMN_INDEX));
                    student.setFirstName(resultSet.getString(THIRD_COLUMN_INDEX));
                    student.setLastName(resultSet.getString(FOURTH_COLUMN_INDEX));
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in Student get(int id)", exception);
        }
        return student;
    }
    
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    student.setGroupId(resultSet.getInt(SECOND_COLUMN_INDEX));
                    student.setFirstName(resultSet.getString(THIRD_COLUMN_INDEX));
                    student.setLastName(resultSet.getString(FOURTH_COLUMN_INDEX));
                    studentList.add(student);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in List<Student> getAll()", exception);
        }
        if (studentList.size() == 0) {
            throw new SQLException("Student list can not be empty");
        }
        return studentList;
    }
    
    @Override
    public void save(List<Student> studentList) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                for (Student student : studentList) {
                    statement.setString(FIRST_COLUMN_INDEX, student.getFirstName());
                    statement.setString(SECOND_COLUMN_INDEX, student.getLastName());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in save(List<Student> studentList)", exception);
        }
    }
    
    @Override
    public void update(Student student) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(FIRST_COLUMN_INDEX, student.getFirstName());
                statement.setString(SECOND_COLUMN_INDEX, student.getLastName());
                statement.setInt(THIRD_COLUMN_INDEX, student.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to update");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in update(Student student)", exception);
        }
    }
    
    @Override
    public void delete(Student student) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {                        
                statement.setInt(FIRST_COLUMN_INDEX, student.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to delete");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in delete(Student student)", exception);
        }
    }
    
    @Override
    public List<Student> searchStudentsByCourseId(int courseId) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_STUDENTS_BY_COURSE_QUERY)) {
                if (courseId <= 0) {
                    throw new SQLException("Course ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, courseId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setFirstName(resultSet.getString(FIRST_COLUMN_INDEX));
                    student.setLastName(resultSet.getString(SECOND_COLUMN_INDEX));
                    studentList.add(student);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException(
                    "SQLException exception in List<Student> searchStudentsByCourseId(int courseId)", exception);
        }
        return studentList;
    }
    
    @Override
    public void addStudentsToGroups(List<Integer> studentId, List<Integer> groupId) throws SQLException {
        Random random = new Random();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENTS_TO_GROUPS_QUERY)) {
                for (Integer id : studentId) {
                    statement.setInt(FIRST_COLUMN_INDEX, groupId.get(random.nextInt(groupId.size())));
                    statement.setInt(SECOND_COLUMN_INDEX, id);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in addStudentsToGroups(List<Integer> studentId, List<Integer> groupId)", exception);
        }
    }
}
