package ua.com.foxminded.sqljdbcschool.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.sqljdbcschool.model.Course;
import ua.com.foxminded.sqljdbcschool.model.FirstNameEnum;
import ua.com.foxminded.sqljdbcschool.model.Group;
import ua.com.foxminded.sqljdbcschool.model.LastNameEnum;
import ua.com.foxminded.sqljdbcschool.model.Student;

public class DataGenerator {   
    public enum CourseEnum { MATH, PHYSICS, CHEMISTRY, ASTRONOMY, BIOLOGY, GEOGRAPHY, LITERATURE, LANGUAGE, PHILOSOPHY, HISTORY
    }
    
    private static final char DILIMETER = '-';
    private static final int NUM_OF_CHARS_BEFORE_DILIMETER = 2;
    private static final int NUM_OF_DIGITS_AFTER_DILIMETER = 2;
    private static final int NUM_OF_NAMES = 20;
    private static final int NUM_OF_GROUPS = 10;
    private static final int A_ASCII_VALUE = 65;
    private static final int Z_ASCII_VALUE = 90;
    private static final int RANDOM_DIGIT_UPPER_BOUND = 10;
    public static final String DILIMETER_SPASE = " ";
    
    public List<Group> generateGroups() {
        List<String> groupNames = generateRandomGroupNames(NUM_OF_GROUPS);
        List<Group> groups = new ArrayList<>();
        for (String groupName : groupNames) {
            groups.add(new Group(groupName));
        }
        return groups;
    }
    
    public List<Student> generateStudents() {
        List<String> firstNames = Stream.of(FirstNameEnum.values())
                .map(FirstNameEnum::name)
                .collect(Collectors.toList());
        List<String> lastNames = Stream.of(LastNameEnum.values())
                .map(LastNameEnum::name)
                .collect(Collectors.toList());
        List<Student> studentList = new ArrayList<>();
        Random random = new Random();
        for (int index = 0; index < 200; index++) {
            Student student = new Student();
            student.setFirstName(firstNames.get(random.nextInt(NUM_OF_NAMES)));
            student.setLastName(lastNames.get(random.nextInt(NUM_OF_NAMES)));
            studentList.add(student);
        }
        return studentList;
    }
    
    private List<String> generateRandomGroupNames(int numOfGroups) {
        List<String> groupNames = new ArrayList<>();
        while (numOfGroups > 0) {
            groupNames.add(generateRandomGroupName());
            numOfGroups--;
        }
        return groupNames;
    }
    
    private String generateRandomGroupName() {
        StringBuilder groupNameBuilder = new StringBuilder();
        Random random = new Random();
        for (int numOfChars = NUM_OF_CHARS_BEFORE_DILIMETER; numOfChars > 0; numOfChars--) {
            groupNameBuilder
                    .append((char) (random.nextInt(Z_ASCII_VALUE - A_ASCII_VALUE + 1) + A_ASCII_VALUE));
        }
        groupNameBuilder.append(DILIMETER);
        for (int numOfDigits = NUM_OF_DIGITS_AFTER_DILIMETER; numOfDigits > 0; numOfDigits--) {
            groupNameBuilder.append(random.nextInt(RANDOM_DIGIT_UPPER_BOUND));
        }
        return groupNameBuilder.toString();
    }
    
    public List<Course> generateCourses() {
        List<String> courseEnum = Stream.of(CourseEnum.values())
                .map(CourseEnum::name)
                .collect(Collectors.toList());
        List<String> courseDiscription = Arrays.asList(
                "Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis",
                "Physics is the natural science that studies matter, its motion and behavior through space and time, and the related entities of energy and force",
                "Chemistry is the scientific discipline involved with elements and compounds composed of atoms, molecules and ions: their composition, structure, properties, behavior and the changes they undergo during a reaction with other substances",
                "Astronomy is a natural science that studies celestial objects and phenomena",
                "Biology is the natural science that studies life and living organisms, including their physical structure, chemical processes, molecular interactions, physiological mechanisms, development and evolution",
                "Geography is a field of science devoted to the study of the lands, features, inhabitants, and phenomena of the Earth and planets",
                "Literature broadly is any collection of written work, but it is also used more narrowly for writings specifically considered to be an art form, especially prose fiction, drama, and poetry",
                "Ukrainian is an East Slavic language. It is the official state language of Ukraine an",
                "Philosophy is the study of general and fundamental questions about existence, knowledge, values, reason, mind, and language",
                "History is the study of the past");       
        List<Course> courseList = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Course course = new Course();
            course.setName(courseEnum.get(index));
            course.setDescription(courseDiscription.get(index));
            courseList.add(course);
        }
        return courseList;
    }
}
