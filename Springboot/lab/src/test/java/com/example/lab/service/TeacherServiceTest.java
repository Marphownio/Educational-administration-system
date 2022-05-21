package com.example.lab.service;

import com.example.lab.pojo.entity.Teacher;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TeacherServiceTest {

    @Autowired
    TeacherService teacherService;

    @Test
    void findTeacherByTeacherId() {
        Teacher teacher = teacherService.findTeacherByTeacherId(20123456);
        assertEquals("123456789123456789", teacher.getIdNumber());
    }

    @Test
    void findAllTeacher() {
        List<Teacher> teacherList = teacherService.findAllTeacher();
        assertEquals(1, teacherList.size());
    }
}
