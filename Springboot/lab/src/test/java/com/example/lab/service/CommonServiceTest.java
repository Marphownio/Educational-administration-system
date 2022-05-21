package com.example.lab.service;

import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CommonServiceTest {

    @Autowired
    CommonService commonService;

    @Test
    void isMatchSchoolAndMajor() {
        School school = new School(); school.setSchoolId(1);
        Major major = new Major(); major.setMajorId(1);
        assertEquals(true, commonService.isMatchSchoolAndMajor(school, major));
    }

    @Test
    void isMatchBuildingAndClassroom() {
        Building building = new Building(); building.setBuildingId(1);
        Classroom classroom = new Classroom(); classroom.setClassroomId(1);
        assertEquals(true, commonService.isMatchBuildingAndClassroom(building, classroom));
    }
}
