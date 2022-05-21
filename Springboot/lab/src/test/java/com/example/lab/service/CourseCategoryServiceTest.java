package com.example.lab.service;

import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CourseCategoryServiceTest {

    @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    void test01AddCourseCategory() {
        School school = new School(); school.setSchoolId(1);
        Major major = new Major(); major.setMajorId(1);

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryId(0);
        courseCategory.setCourseCategoryNumber("courseCategory_test");
        courseCategory.setCourseName("course_test");
        courseCategory.setSchool(school);
        courseCategory.setMajor(major);
        courseCategory.setClassHour(1);
        courseCategory.setCredit(1);
        assertSame(ResultMessage.SUCCESS, courseCategoryService.addCourseCategory(courseCategory));
    }

    @Test
    void test02UpdateCourseCategory() {
        School school = new School(); school.setSchoolId(1);
        Major major = new Major(); major.setMajorId(1);

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryId(3);
        courseCategory.setCourseCategoryNumber("courseCategory_test");
        courseCategory.setCourseName("course_test");
        courseCategory.setSchool(school);
        courseCategory.setMajor(major);
        courseCategory.setClassHour(2);
        courseCategory.setCredit(2);
        assertSame(ResultMessage.SUCCESS, courseCategoryService.updateCourseCategory(courseCategory));
    }

    @Test
    void test03FindAllCourseCategory() {
        List<CourseCategory> courseCategoryList = courseCategoryService.findAllCourseCategory();
        assertEquals(2, courseCategoryList.size());
    }

    @Test
    void test04FindCourseByTermInCourseCategory() {
        List<Course> courseList = courseCategoryService.findCourseByTermInCourseCategory(1, "2021-2022", "1");
        assertEquals(3, courseList.size());
    }

    @Test
    void test05FindCourseCategoryByCourseCategoryId() {
        CourseCategory courseCategory = courseCategoryService.findCourseCategoryByCourseCategoryId(1);
        assertEquals("courseCategory_1", courseCategory.getCourseCategoryNumber());
    }

    @Test
    void test06FindCourseCategoryByCourseName() {
        CourseCategory courseCategory = courseCategoryService.findCourseCategoryByCourseName("course");
        assertEquals("courseCategory_1", courseCategory.getCourseCategoryNumber());
    }

    @Test
    void test07DeleteCourseCategory() {
        assertSame(ResultMessage.SUCCESS, courseCategoryService.deleteCourseCategory(3));
    }
}
