package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class StudentApplicationServiceTest {

    @Autowired
    StudentApplicationService studentApplicationService;

    @Test
    void test01AddStudentApplication() {
        assertSame(ResultMessage.SUCCESS, studentApplicationService.addStudentApplication(2, 200002, "test"));
    }

    @Test
    void test02FindAllStudentApplication() {
        assertEquals(1, studentApplicationService.findAllStudentApplication().size());
    }

    @Test
    void test03FindStudentApplication() {
        assertEquals(1, studentApplicationService.findStudentApplication(200002).size());
    }

    @Test
    void test04FindStudentApplicationById() {
        assertEquals("test", studentApplicationService.findStudentApplicationById(1).getReason());
    }

    @Test
    void test05ProcessStudentApplication() {
        assertEquals(ResultMessage.SUCCESS, studentApplicationService.processStudentApplication(1, true));
    }

    @Test
    void test06DeleteStudentApplication() {
        assertSame(ResultMessage.SUCCESS, studentApplicationService.deleteStudentApplication(1));
    }
}
