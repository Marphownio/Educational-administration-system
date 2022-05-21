package com.example.lab.service;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Test
    void test01AddCourse() {
        School school = new School(); school.setSchoolId(1);
        Major major1 = new Major(); major1.setMajorId(1);
        Major major2 = new Major(); major2.setMajorId(2);
        Major major3 = new Major(); major3.setMajorId(3);
        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryNumber("courseCategory_1");
        courseCategory.setCourseName("course");
        courseCategory.setSchool(school);
        courseCategory.setMajor(major1);
        courseCategory.setClassHour(1);
        courseCategory.setCredit(1);
        Teacher teacher = new Teacher(); teacher.setUserId(20123456);

        Set<ClassArrangement> classArrangements1 = new HashSet<>();
        Set<ClassArrangement> classArrangements2 = new HashSet<>();
        Set<ClassArrangement> classArrangements3 = new HashSet<>();
        Set<ClassArrangement> classArrangements4 = new HashSet<>();

        ClassArrangement classArrangement = new ClassArrangement();

        Building building = new Building(); building.setBuildingId(1);
        Classroom classroom = new Classroom(); classroom.setClassroomId(1);
        ClassTime classTime = new ClassTime(); classTime.setClassTimeId(1);

        classArrangement.setBuilding(building);
        classArrangement.setClassroom(classroom);
        classArrangement.setDayOfWeek(DayOfWeek.FRIDAY);
        classArrangement.getClassTimes().add(classTime);
        classArrangements1.add(classArrangement);

        Set<Major> openToMajors = new HashSet<>();

        openToMajors.add(major1);
        Course course1 = new Course();
        course1.setCapacity(80);
        course1.setAcademicYear("2021-2022");
        course1.setTerm("1");
        course1.setCourseCategory(courseCategory);
        course1.setOpenToMajors(openToMajors);
        course1.setClassArrangements(classArrangements1);
        course1.setTeacher(teacher);
        assertSame(ResultMessage.SUCCESS, courseService.addCourse(course1));

        openToMajors.add(major2);
        Course course2 = new Course();
        course2.setCapacity(80);
        course2.setAcademicYear("2021-2022");
        course2.setTerm("1");
        course2.setCourseCategory(courseCategory);
        course2.setOpenToMajors(openToMajors);
        classArrangement.setDayOfWeek(DayOfWeek.TUESDAY);
        classArrangements2.add(classArrangement);
        course2.setClassArrangements(classArrangements2);
        course2.setTeacher(teacher);
        assertSame(ResultMessage.SUCCESS, courseService.addCourse(course2));

        openToMajors.add(major3);
        Course course3 = new Course();
        course3.setCapacity(80);
        course3.setAcademicYear("2021-2022");
        course3.setTerm("1");
        course3.setCourseCategory(courseCategory);
        course3.setOpenToMajors(openToMajors);
        classArrangement.setDayOfWeek(DayOfWeek.WEDNESDAY);
        classArrangements3.add(classArrangement);
        course3.setClassArrangements(classArrangements3);
        course3.setTeacher(teacher);
        assertSame(ResultMessage.SUCCESS, courseService.addCourse(course3));

        Course course4 = new Course();
        course4.setCapacity(80);
        course4.setAcademicYear("2021-2022");
        course4.setTerm("1");
        course4.setCourseCategory(courseCategory);
        course4.setOpenToMajors(openToMajors);
        classArrangement.setDayOfWeek(DayOfWeek.THURSDAY);
        classArrangements4.add(classArrangement);
        course4.setClassArrangements(classArrangements4);
        course4.setTeacher(teacher);
        assertSame(ResultMessage.SUCCESS, courseService.addCourse(course4));
    }

    @Test
    void test02UpdateCourse() {
        School school = new School(); school.setSchoolId(1);
        Major major1 = new Major(); major1.setMajorId(1);

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryNumber("courseCategory_1");
        courseCategory.setCourseName("course");
        courseCategory.setSchool(school);
        courseCategory.setMajor(major1);
        courseCategory.setClassHour(1);
        courseCategory.setCredit(1);

        Teacher teacher = new Teacher(); teacher.setUserId(20123456);

        Set<ClassArrangement> classArrangements = new HashSet<>();
        ClassArrangement classArrangement = new ClassArrangement();
        Building building = new Building(); building.setBuildingId(1);
        Classroom classroom = new Classroom(); classroom.setClassroomId(1);
        classArrangement.setBuilding(building);
        classArrangement.setClassroom(classroom);
        classArrangement.setDayOfWeek(DayOfWeek.SUNDAY);
        ClassTime classTime = new ClassTime(); classTime.setClassTimeId(1);
        classArrangement.getClassTimes().add(classTime);
        classArrangements.add(classArrangement);

        Set<Major> openToMajors = new HashSet<>();

        openToMajors.add(major1);

        Course course4 = new Course();
        course4.setCourseId(4);
        course4.setCapacity(100);
        course4.setAcademicYear("2021-2022");
        course4.setTerm("2");
        course4.setCourseCategory(courseCategory);
        course4.setOpenToMajors(openToMajors);
        course4.setClassArrangements(classArrangements);
        course4.setTeacher(teacher);

        assertSame(ResultMessage.SUCCESS, courseService.updateCourse(course4));
    }

    @Test
    void test03FindAllCourse() {
        List<Course> courseList = courseService.findAllCourse();
        assertEquals(4, courseList.size());
    }

    @Test
    void test04FindCourseByTerm() {
        List<Course> courseList = courseService.findCourseByTerm("2021-2022", "1");
        assertEquals(3, courseList.size());
    }

    @Test
    void test05FindCourseByCourseId() {
        Course course = courseService.findCourseByCourseId(1);
        assertEquals(1, course.getOpenToMajors().size());
        assertEquals(20123456, course.getTeacher().getUserId());
    }

    @Test
    void test06DeleteCourse() {
        assertSame(ResultMessage.SUCCESS, courseService.deleteCourse(4));
    }
}
