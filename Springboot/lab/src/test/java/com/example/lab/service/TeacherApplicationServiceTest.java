package com.example.lab.service;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ApplicationType;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TeacherApplicationServiceTest {

    @Autowired
    TeacherApplicationService teacherApplicationService;

    @Test
    void test01AddTeacherApplication() {

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseName("course");

        Teacher teacher = new Teacher(); teacher.setUserId(20123456);

        Set<ClassArrangement> classArrangements = new HashSet<>();

        ClassArrangement classArrangement = new ClassArrangement();

        Building building = new Building(); building.setBuildingId(1);
        Classroom classroom = new Classroom(); classroom.setClassroomId(1);
        ClassTime classTime = new ClassTime(); classTime.setClassTimeId(1);

        classArrangement.setBuilding(building);
        classArrangement.setClassroom(classroom);
        classArrangement.setDayOfWeek(DayOfWeek.SATURDAY);
        classArrangement.getClassTimes().add(classTime);
        classArrangements.add(classArrangement);

        Set<Major> openToMajors = new HashSet<>();

        Major major = new Major(); major.setMajorId(1);
        openToMajors.add(major);

        TeacherApplication teacherApplication = new TeacherApplication();
        teacherApplication.setCapacity(80);
        teacherApplication.setCourseCategory(courseCategory);
        teacherApplication.setOpenToMajors(openToMajors);
        teacherApplication.setClassArrangements(classArrangements);
        teacherApplication.setTeacher(teacher);
        teacherApplication.setType(ApplicationType.ADD);
        assertSame(ResultMessage.SUCCESS, teacherApplicationService.addTeacherApplication(teacherApplication));
    }

    @Test
    void test02UpdateTeacherApplication() {
        TeacherApplication teacherApplication = teacherApplicationService.findTeacherApplicationById(1);
        teacherApplication.setIntroduction("test");
        assertSame(ResultMessage.SUCCESS, teacherApplicationService.updateTeacherApplication(teacherApplication));
    }

    @Test
    void test03FindAllTeacherApplication() {
        List<TeacherApplication> teacherApplicationList = teacherApplicationService.findAllTeacherApplication();
        assertEquals(1, teacherApplicationList.size());
    }

    @Test
    void test04FindTeacherApplicationById() {
        TeacherApplication teacherApplication = teacherApplicationService.findTeacherApplicationById(1);
        assertEquals("test", teacherApplication.getIntroduction());
    }

    @Test
    void test05ProcessTeacherApplication() {
        assertSame(ResultMessage.SUCCESS, teacherApplicationService.processTeacherApplication(1, true));
    }

    @Test
    void test06DeleteTeacherApplication() {
        assertSame(ResultMessage.SUCCESS, teacherApplicationService.deleteTeacherApplication(1));
    }
}
