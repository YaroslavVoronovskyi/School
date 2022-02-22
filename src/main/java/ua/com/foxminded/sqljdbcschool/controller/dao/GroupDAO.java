package ua.com.foxminded.sqljdbcschool.controller.dao;

import java.sql.SQLException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.model.Group;

public interface GroupDAO {
    public Group get(int id) throws SQLException;
    
    public List<Group> getAll() throws SQLException;
    
    public void save(List<Group> groupList) throws SQLException;
    
    public void update(Group group) throws SQLException;
    
    public void delete(Group group) throws SQLException;

    public List<Group> searchtGroupsWithLessStudentCount(int studentId) throws SQLException;
}
