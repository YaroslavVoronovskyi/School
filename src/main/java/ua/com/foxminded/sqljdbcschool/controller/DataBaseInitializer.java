package ua.com.foxminded.sqljdbcschool.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.controller.dao.CourseDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.GroupDAOImpl;
import ua.com.foxminded.sqljdbcschool.controller.dao.StudentDAOImpl;
import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class DataBaseInitializer {
    private DataGenerator dataGenerator = new DataGenerator();
    private CourseDAOImpl courseDAOImpl = new CourseDAOImpl();
    private GroupDAOImpl groupDAOImpl = new GroupDAOImpl();
    private StudentDAOImpl studentDAOImpl = new StudentDAOImpl();
    private SchoolService schoolService = new SchoolService(courseDAOImpl, groupDAOImpl, studentDAOImpl);
    
    public void initTableSchool() throws SQLException {
        List<Course> courseList = dataGenerator.generateCourses();
        List<Group> groupList = dataGenerator.generateGroups();
        List<Student> studentList = dataGenerator.generateStudents();
        schoolService.addCourses(courseList);
        schoolService.addGroups(groupList);
        schoolService.addStudents(studentList);
        addStudentsToCourses();
        addStudentsToGroups();
    }
    
    private void addStudentsToCourses() throws SQLException {
        Random random = new Random();
        int courseIndex = Integer.MAX_VALUE;
        List<Student> studentIdList = schoolService.getAllStudents();
        List<Course> courseIdList = schoolService.getAllCourses();
        Course course = null;
        for (Student student : studentIdList) {
            for (int index = random.nextInt(3) + 1; index > 0; index--) {
                if (courseIndex >= courseIdList.size()) {
                    Collections.shuffle(courseIdList);
                    courseIndex = 0;
                }
                course = courseIdList.get(courseIndex);
                schoolService.addStudentToCourse(student.getId(), course.getId());
                courseIndex++;
            }
            courseIndex = Integer.MAX_VALUE;
        }
    }
    
    private void addStudentsToGroups() throws SQLException {
        List<Integer> studentId = new ArrayList<>();
        List<Integer> groupId = new ArrayList<>();
        List<Student> studentIdList = schoolService.getAllStudents();
        for (Student student : studentIdList) {
            studentId.add(student.getId());
        }
        List<Group> groupIdList = schoolService.getAllGroups();
        for (Group group : groupIdList) {
            groupId.add(group.getId());
        }
        schoolService.addStudentsToGroups(studentId, groupId);
    }
}
