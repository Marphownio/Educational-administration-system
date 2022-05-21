package com.example.lab.service;

import com.example.lab.pojo.entity.Building;
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
class ClassroomServiceTest {

    @Autowired
    ClassroomService classroomService;

    @Test
    void test01AddClassroom() {
        Building building = new Building();
        building.setBuildingId(1);

        Classroom classroom1 = new Classroom();
        classroom1.setClassroomId(1);
        classroom1.setCapacity(100);
        classroom1.setBuilding(building);
        assertSame(ResultMessage.SUCCESS, classroomService.addClassroom(classroom1));

        Classroom classroom2 = new Classroom();
        classroom2.setClassroomId(2);
        classroom2.setCapacity(100);
        classroom2.setBuilding(building);
        assertSame(ResultMessage.SUCCESS, classroomService.addClassroom(classroom2));
    }

    @Test
    void test02UpdateClassroom() {
        Building building = new Building();
        building.setBuildingId(1);

        Classroom classroom2 = new Classroom();
        classroom2.setClassroomId(2);
        classroom2.setCapacity(120);
        classroom2.setBuilding(building);
        assertSame(ResultMessage.SUCCESS, classroomService.updateClassroom(classroom2));
    }

    @Test
    void test03FindClassroomById() {
        Classroom classroom = classroomService.findClassroomById(1);
        assertNotNull(classroom);
        assertEquals(1, classroom.getClassroomId());
        assertEquals(100, classroom.getCapacity());
        assertEquals(1, classroom.getBuilding().getBuildingId());
    }

    @Test
    void test04FindAllClassroom() {
        List<Classroom> classroomList = classroomService.findAllClassroom();
        assertNotNull(classroomList);
        assertEquals(2, classroomList.size());
        assertEquals(1, classroomList.get(0).getClassroomId());
        assertEquals(2, classroomList.get(1).getClassroomId());
    }

    @Test
    void test05DeleteClassroom() {
        assertSame(ResultMessage.SUCCESS, classroomService.deleteClassroom(2));
    }
}
