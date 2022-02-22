package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.connection.DataSource;
import ua.com.foxminded.sqljdbcschool.model.Group;

public class GroupDAOImpl implements GroupDAO {
    private static final String GET_QUERY = "SELECT * FROM groups WHERE group_id = ?";
    private static final String GETALL_QUERY = "SELECT * FROM groups";
    private static final String INSERT_QUERY = "INSERT INTO groups(group_name) VALUES(?)";
    private static final String UPDATE_QUERY = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM groups WHERE group_id = ?";
    private static final String GET_LESS_STUDENTS_COUNT_QUERY = "SELECT groups.group_name FROM groups "
            + "LEFT JOIN students ON groups.group_id = students.group_ref GROUP BY groups.group_id "
            + "HAVING COUNT(students.student_id) <= ? ORDER BY groups.group_id";
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int SECOND_COLUMN_INDEX = 2;
    
    @Override
    public Group get(int id) throws SQLException {
        Group group = null;
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
                if (id <= 0) {
                    throw new SQLException("Group ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    group = new Group();
                    group.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    group.setName(resultSet.getString(SECOND_COLUMN_INDEX));
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in Group get(int id)", exception);
        }
        return group;
    }
    
    @Override
    public List<Group> getAll() throws SQLException {
        List<Group> groupList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GETALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt(FIRST_COLUMN_INDEX));
                    group.setName(resultSet.getString(SECOND_COLUMN_INDEX));
                    groupList.add(group);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in List<Group> getAll()", exception);
        }
        if (groupList.size() == 0) {
            throw new SQLException("Student list can not be empty");
        }
        return groupList;
    }
    
    @Override
    public void save(List<Group> groupList) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                for (Group group : groupList) {
                    statement.setString(FIRST_COLUMN_INDEX, group.getName());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in save(List<Group> groupList)", exception);
        }
    }
    
    @Override
    public void update(Group group) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(FIRST_COLUMN_INDEX, group.getName());
                statement.setInt(SECOND_COLUMN_INDEX, group.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to update");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in update(Group group)", exception);
        }
    }
    
    @Override
    public void delete(Group group) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setInt(FIRST_COLUMN_INDEX, group.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException("The record doesn't exist. Not possible to delete");
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in delete(Group group)", exception);
        }
    }
    
    @Override
    public List<Group> searchtGroupsWithLessStudentCount(int studentId) throws SQLException {
        List<Group> groupList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_LESS_STUDENTS_COUNT_QUERY)) {
                if (studentId <= 0) {
                    throw new SQLException("Student ID must be positiv");
                }
                statement.setInt(FIRST_COLUMN_INDEX, studentId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setName(resultSet.getString(FIRST_COLUMN_INDEX));
                    groupList.add(group);
                }
            }
        } catch (SQLException exception) {
            throw new SQLException("SQLException exception in List<Group> searchtGroupsWithLessStudentCount()", exception);
        }
        return groupList;
    }
}
