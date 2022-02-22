package ua.com.foxminded.sqljdbcschool.controller.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class GroupDAOImplTest {
    private static final String CREATE_TABLES = "ScriptSchool.sql";
    private static final String CREATE_GROUPS = "ScriptInsertGroups.sql";
    private static final String CREATE_STUDENTS = "ScriptInsertStudents.sql";
    private static final String CREATE_COURSES = "ScriptInsertCourses.sql";
    
    DataScriptExecution dataScriptExecution = new DataScriptExecution();
    DataGenerator dataGenerator = new DataGenerator();
    GroupDAOImpl groupDAOImpl = new GroupDAOImpl();
    StudentDAOImpl studentDAOImpl = new StudentDAOImpl();    
    
    @BeforeEach
    public void runScriptShouldCreateAndInitTables() throws SQLException, ClassNotFoundException, IOException {
        dataScriptExecution.scriptRunner(CREATE_TABLES);
        dataScriptExecution.scriptRunner(CREATE_STUDENTS);
        dataScriptExecution.scriptRunner(CREATE_COURSES);
        dataScriptExecution.scriptRunner(CREATE_GROUPS);
    }
    
    @Test
    public void shouldReturnExpectedNumberOfGroups() throws SQLException {
        List<Group> groupList = groupDAOImpl.getAll();
        int expected = 10;
        int actual = groupList.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void shouldReturnExpectedIdOfGroups() throws SQLException {
        int groupId = 1;
        Group expectedGroup = new Group(1, "QW-11");
        Group actualGroup = groupDAOImpl.get(groupId); 
        assertEquals(expectedGroup.getId(), actualGroup.getId());
        assertEquals(expectedGroup.getName(), actualGroup.getName());
    }
    
    @Test
    public void shouldDeleteExpectedIdOfGroup() throws SQLException {
        Group expectedGroup = new Group(1);
        Group actualGroup = new Group(1);
        groupDAOImpl.delete(actualGroup);
        assertEquals(expectedGroup.getId(), actualGroup.getId());
        assertEquals(expectedGroup.getName(), actualGroup.getName());
    }
    
    @Test
    public void shouldInitTableGroup() throws SQLException {
        Group groupEleven = new Group(11, "QQ-11");
        List<Group> expectedGroupList = new ArrayList<>();
        expectedGroupList.add(groupEleven);
        groupDAOImpl.save(expectedGroupList);    
        assertNotNull(groupDAOImpl.get(11));
        assertTrue(groupDAOImpl.getAll().contains(groupEleven));
    }
    
    @Test
    public void shouldUpdateGroup() throws SQLException {
        Group expectedGroup = new Group(1, "FF-22");
        groupDAOImpl.update(expectedGroup);
        Group actualGroup = groupDAOImpl.get(1);
        assertNotNull(actualGroup);
        assertEquals(expectedGroup, actualGroup);
    }
    
    @Test
    public void shouldSearchtGroupsWithLessStudentCount() throws SQLException {
        Student student = studentDAOImpl.get(1);
        Group group = groupDAOImpl.get(1);
        List<Integer> studentId = new ArrayList<>();
        studentId.add(student.getId());
        List<Integer> groupId = new ArrayList<>();
        groupId.add(group.getId());
        studentDAOImpl.addStudentsToGroups(studentId, groupId);
        assertNotNull(groupDAOImpl.searchtGroupsWithLessStudentCount(student.getId()).add(group));
        assertTrue(groupDAOImpl.searchtGroupsWithLessStudentCount(student.getId()).add(group)); 
    }
    
    @Test
    public void saveShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Group group = new Group();        
        List<Group> groupList = new ArrayList<>();
        group.setId(1);
        groupList.add(group);
        assertThrows(SQLException.class, () -> groupDAOImpl.save(groupList));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectInputData() throws SQLException {
        Group group = groupDAOImpl.get(1);      
        group.setId(2);    
        assertThrows(SQLException.class, () -> groupDAOImpl.update(group));
    }
    
    @Test
    public void updateShouldThrowSQLExceptionThroughIncorrectInputDataThroughIncorrectZeroInputData() throws SQLException {
        Group group = groupDAOImpl.get(1);      
        group.setId(0);    
        assertThrows(SQLException.class, () -> groupDAOImpl.update(group));
    }
    
    @Test
    public void deleteShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {       
        Group group = new Group(-1);
        assertThrows(SQLException.class, () -> groupDAOImpl.delete(group));
    }
    
    @Test
    public void getShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        assertThrows(SQLException.class, () -> groupDAOImpl.get(-1));
    }
    
    @Test
    public void searchtGroupsWithLessStudentCountShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        assertThrows(SQLException.class, () -> groupDAOImpl.searchtGroupsWithLessStudentCount(-1));
    }
    
    @Test
    public void getAllShouldThrowSQLExceptionThroughIncorrectInputData() throws SQLException {
        Group group1 = groupDAOImpl.get(1);
        groupDAOImpl.delete(group1);
        Group group2 = groupDAOImpl.get(2);
        groupDAOImpl.delete(group2);
        Group group3 = groupDAOImpl.get(3);
        groupDAOImpl.delete(group3);
        Group group4 = groupDAOImpl.get(4);
        groupDAOImpl.delete(group4);
        Group group5 = groupDAOImpl.get(5);
        groupDAOImpl.delete(group5);
        Group group6 = groupDAOImpl.get(6);
        groupDAOImpl.delete(group6);
        Group group7 = groupDAOImpl.get(7);
        groupDAOImpl.delete(group7);
        Group group8 = groupDAOImpl.get(8);
        groupDAOImpl.delete(group8);
        Group group9 = groupDAOImpl.get(9);
        groupDAOImpl.delete(group9);
        Group group10 = groupDAOImpl.get(10);
        groupDAOImpl.delete(group10);
        assertThrows(SQLException.class, () -> groupDAOImpl.getAll());
    }
}
