package com.example.lab.service;

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
class SchoolServiceTest {

    @Autowired
    SchoolService schoolService;

    @Test
    void test01AddSchool() {
        School school1 = new School();
        school1.setSchoolId(1);
        school1.setSchoolName("school_1");
        school1.setIntroduction("school_1");
        assertSame(ResultMessage.SUCCESS, schoolService.addSchool(school1));

        School school2 = new School();
        school2.setSchoolId(2);
        school2.setSchoolName("school_2");
        school2.setIntroduction("暂无介绍");
        assertSame(ResultMessage.SUCCESS, schoolService.addSchool(school2));
    }

    @Test
    void test02UpdateSchool() {
        School school2 = new School();
        school2.setSchoolId(2);
        school2.setSchoolName("school_2");
        school2.setIntroduction("school_2");
        assertSame(ResultMessage.SUCCESS, schoolService.updateSchool(school2));
    }

    @Test
    void test03FindAllSchool() {
        List<School> schoolList = schoolService.findAllSchool();
        assertNotNull(schoolList);
        assertEquals(2, schoolList.size());
        assertEquals(1, schoolList.get(0).getSchoolId());
        assertEquals(2, schoolList.get(1).getSchoolId());
    }

    @Test
    void test04FindSchoolBySchoolId() {
        School school = schoolService.findSchoolBySchoolId(1);
        assertNotNull(school);
        assertEquals(1, school.getSchoolId());
        assertEquals("school_1", school.getSchoolName());
        assertEquals("school_1", school.getIntroduction());
    }

    @Test
    void test05FindSchoolBySchoolName() {
        School school = schoolService.findSchoolBySchoolName("school_1");
        assertNotNull(school);
        assertEquals(1, school.getSchoolId());
        assertEquals("school_1", school.getSchoolName());
        assertEquals("school_1", school.getIntroduction());
    }

    @Test
    void test06DeleteSchool() {
        assertSame(ResultMessage.SUCCESS, schoolService.deleteSchool(2));
    }
}
