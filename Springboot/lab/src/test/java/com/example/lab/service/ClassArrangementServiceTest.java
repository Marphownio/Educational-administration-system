package com.example.lab.service;

import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClassArrangementServiceTest {

    @Autowired
    ClassArrangementService classArrangementService;

    @Test
    void test01AddClassArrangement() {
        ClassArrangement classArrangement = new ClassArrangement();
        Building building = new Building(); building.setBuildingId(1);
        Classroom classroom = new Classroom(); classroom.setClassroomId(1);
        ClassTime classTime = new ClassTime(); classTime.setClassTimeId(1);
        classArrangement.setClassArrangementId(0);
        classArrangement.setBuilding(building);
        classArrangement.setClassroom(classroom);
        classArrangement.setDayOfWeek(DayOfWeek.SUNDAY);
        classArrangement.getClassTimes().add(classTime);
        assertEquals(8, classArrangementService.addClassArrangement(classArrangement).getClassArrangementId());
    }

    @Test
    void test02DeleteClassArrangement() {
        assertSame(ResultMessage.SUCCESS, classArrangementService.deleteClassArrangement(8));
    }
}
