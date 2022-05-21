package com.example.lab.service;

import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.pojo.entity.Classroom;
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
class ClassTimeServiceTest {

    @Autowired
    ClassTimeService classTimeService;

    @Test
    void test01AddClassTime() {
        ClassTime classTime1 = new ClassTime(1, 8, 0, 8, 45);
        ClassTime classTime2 = new ClassTime(2, 8, 55, 9, 40);
        ClassTime classTime3 = new ClassTime(3, 9, 55, 10, 40);
        ClassTime classTime4 = new ClassTime(4, 10, 50, 11, 35);
        assertSame(ResultMessage.SUCCESS, classTimeService.addClassTime(classTime1));
        assertSame(ResultMessage.SUCCESS, classTimeService.addClassTime(classTime2));
        assertSame(ResultMessage.SUCCESS, classTimeService.addClassTime(classTime3));
        assertSame(ResultMessage.SUCCESS, classTimeService.addClassTime(classTime4));
    }

    @Test
    void test02UpdateClassTime() {
        ClassTime classTime4 = new ClassTime(4, 10, 50, 11, 40);
        assertSame(ResultMessage.SUCCESS, classTimeService.updateClassTime(classTime4));
    }

    @Test
    void test03FindAllClassTime() {
        List<ClassTime> classTimeList = classTimeService.findAllClassTime();
        assertEquals(4, classTimeList.size());
    }

    @Test
    void test04FindClassTimeById() {
        ClassTime classTime = classTimeService.findClassTimeById(1);
        assertEquals(8, classTime.getStartTimeHour());
        assertEquals(0, classTime.getStartTimeMin());
        assertEquals(8, classTime.getEndTimeHour());
        assertEquals(45, classTime.getEndTimeMin());
    }

    @Test
    void test05DeleteClassTime() {
        assertSame(ResultMessage.SUCCESS, classTimeService.deleteClassTime(4));
    }
}
