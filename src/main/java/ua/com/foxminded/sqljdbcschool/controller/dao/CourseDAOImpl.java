package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.connection.DataSource;
import ua.com.foxminded.sqljdbcschool.model.Course;

public class CourseDAOImpl implements CourseDAO {
    private static final String GET_QUERY = "SELECT * FROM courses WHERE course_id = ?";
    private static final String GETALL_QUERY = "SELECT * FROM courses";
    private static final String INSERT_QUERY = "INSERT INTO courses(course_name, course_description) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM courses WHERE course_id = ?";
    private static final String GET_COURSE_BY_STUDENTS_QUERY = "SELECT * FROM courses LEFT JOIN students ON course_id = student_id WHERE course_id = ?";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO students_courses (student_ref, course_ref) VALUES (?, ?)";
    private static final String DELETE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM students_courses WHERE student_ref = ? AND course_ref = ?"; 
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int SECOND_COLUMN_INDEX = 2;
    private static final int THIRD_COLUMN_INDEX = 3;
    
    @Override
    public Course get(int id) throws SQLException {
        Course course = null;
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
                if (id <= 0) {
                    throw new SQLException("Course ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    course = new Course();
                    course.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    course.setName(resultSet.getString(SECOND_COLUMN_INDEX ));
                    course.setDescription(resultSet.getString(THIRD_COLUMN_INDEX));   
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in Course get(int id)", exception);
        }
        return course;
    }
    
    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GETALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    course.setName(resultSet.getString(SECOND_COLUMN_INDEX));
                    course.setDescription(resultSet.getString(THIRD_COLUMN_INDEX));
                    courseList.add(course);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in List<Course> getAll()", exception);
        }
        if (courseList.size() == 0) {
            throw new SQLException("Student list can not be empty");
        }
        return courseList;
    }
    
    @Override
    public void save(List<Course> courseList) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                for (Course course : courseList) {
                    statement.setString(FIRST_COLUMN_INDEX, course.getName());
                    statement.setString(SECOND_COLUMN_INDEX, course.getDescription());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in save(List<Course> courseList)", exception);
        }
    }
    
    @Override
    public void update(Course course) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {                
                statement.setString(FIRST_COLUMN_INDEX, course.getName());
                statement.setString(SECOND_COLUMN_INDEX, course.getDescription());
                statement.setInt(THIRD_COLUMN_INDEX, course.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to update");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in update(Course course)", exception);
        }
    }
    
    @Override
    public void delete(Course course) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setInt(FIRST_COLUMN_INDEX, course.getId());               
                int result = statement.executeUpdate();
                if(result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to delete");
                }
                }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in delete(Course course)", exception);
        }
    }
    
    @Override
    public void deleteStudentFromCourse(Integer studentId, Integer courseId) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE_QUERY)) {
                statement.setInt(FIRST_COLUMN_INDEX, studentId);
                statement.setInt(SECOND_COLUMN_INDEX, courseId);
                int result = statement.executeUpdate();
                if(result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to delete");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in deleteStudentFromCourse(Integer studentId, Integer courseId)", exception);
        }
    }
    
    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_TO_COURSE_QUERY)) {
                statement.setInt(FIRST_COLUMN_INDEX, studentId);
                statement.setInt(SECOND_COLUMN_INDEX, courseId);
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in addStudentToCourse(Integer studentId, Integer courseId)", exception);
        }
    }
    
    @Override
    public List<Course> searchCourseByStudent(int studentId) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_COURSE_BY_STUDENTS_QUERY)) {
                if (studentId <= 0) {
                    throw new SQLException("Student ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, studentId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    course.setName(resultSet.getString(SECOND_COLUMN_INDEX));
                    course.setDescription(resultSet.getString(THIRD_COLUMN_INDEX));
                    courseList.add(course);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in List<Course> getAll()", exception);
        }
        return courseList;
    }
}
