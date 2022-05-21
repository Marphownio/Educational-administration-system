package com.example.lab.service;

import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @Test
    void test01FindStudentByStudentId() {
        Student student = studentService.findStudentByStudentId(200001);
        assertEquals("123456789123456781", student.getIdNumber());
    }

    @Test
    void test02FindAllStudent() {
        List<Student> studentList = studentService.findAllStudent();
        assertEquals(3, studentList.size());
    }

    @Test
    void test03ChangeCourseSelectionStatus() {
        assertSame(ResultMessage.SUCCESS, studentService.changeCourseSelectionStatus());
    }

    @Test
    void test04SelectCourse() {
        assertEquals(ResultMessage.SUCCESS, studentService.selectCourse(200001, 1));
        assertEquals(ResultMessage.SUCCESS, studentService.selectCourse(200002, 2));
        assertEquals(ResultMessage.EXIST, studentService.selectCourse(200002, 3));
    }

    @Test
    void test05GetSelectableCourse() {
        Set<Course> courseSet = studentService.getSelectableCourse(200001);
        assertEquals(2, courseSet.size());
    }

    @Test
    void test06FindAllCoursesStudying() {
        Set<Course> courseSet = studentService.findAllCoursesStudying(200001);
        assertEquals(1, courseSet.size());
    }

    @Test
    void test07FindAllCoursesSpecified() {
        Set<Course> courseSet = studentService.findAllCoursesSpecified(200002, "2021-2022", "1");
        assertEquals(1, courseSet.size());
    }

    @Test
    void test08FindAllCoursesCompleted() {
        Set<Course> courseSet = studentService.findAllCoursesCompleted(200002);
        assertEquals(0, courseSet.size());
    }

    @Test
    void test09UpdateStudent() {
        Student student = studentService.findStudentByStudentId(200003);
        student.setEmail("test@qq.com");
        assertEquals(ResultMessage.SUCCESS, studentService.updateStudent(student));
    }

    @Test
    void test10DropCourse() {
        assertEquals(ResultMessage.SUCCESS, studentService.dropCourse(200002, 2));
    }
}
