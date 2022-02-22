package ua.com.foxminded.sqljdbcschool;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;

import ua.com.foxminded.sqljdbcschool.connection.DataScriptExecution;
import ua.com.foxminded.sqljdbcschool.controller.DataBaseInitializer;
import ua.com.foxminded.sqljdbcschool.controller.SchoolService;
import ua.com.foxminded.sqljdbcschool.controller.dao.CourseDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.GroupDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.StudentDAOImpl;
import ua.com.foxminded.sqljdbcschool.view.SchoolMenu;

public class Main {
    public static void main(String[] args) {
        final String CREATE_SCRIPT = "ScriptSchool.sql";
        try {
            DataScriptExecution scriptExecution = new DataScriptExecution();
            scriptExecution.scriptRunner(CREATE_SCRIPT);
            DataBaseInitializer dataBaseInitializer = new DataBaseInitializer();
            dataBaseInitializer.initTableSchool();
            CourseDAOImpl courseDAOImpl = new CourseDAOImpl();
            GroupDAOImpl groupDAOImpl = new GroupDAOImpl();
            StudentDAOImpl studentDAOImpl = new StudentDAOImpl();
            SchoolService schoolService = new SchoolService(courseDAOImpl, groupDAOImpl, studentDAOImpl);
            SchoolMenu schoolMenu = new SchoolMenu(schoolService);
            schoolMenu.runSchoolMenu();
        } catch (ClassNotFoundException | SQLException | IOException | InputMismatchException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}