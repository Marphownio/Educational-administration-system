package com.example.lab.service;

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
class MajorServiceTest {

    @Autowired
    MajorService majorService;

    @Test
    void test01AddMajor() {
        School school = new School();
        school.setSchoolId(1);

        Major major1 = new Major();
        major1.setMajorId(1);
        major1.setMajorName("major_1");
        major1.setIntroduction("major_1");
        major1.setSchool(school);
        assertSame(ResultMessage.SUCCESS, majorService.addMajor(major1));

        Major major2 = new Major();
        major2.setMajorId(2);
        major2.setMajorName("major_2");
        major2.setIntroduction("暂无介绍");
        major2.setSchool(school);
        assertSame(ResultMessage.SUCCESS, majorService.addMajor(major2));

        Major major3 = new Major();
        major3.setMajorId(3);
        major3.setMajorName("major_3");
        major3.setIntroduction("major_3");
        major3.setSchool(school);
        assertSame(ResultMessage.SUCCESS, majorService.addMajor(major3));

        Major major4 = new Major();
        major4.setMajorId(4);
        major4.setMajorName("major_4");
        major4.setIntroduction("major_4");
        major4.setSchool(school);
        assertSame(ResultMessage.SUCCESS, majorService.addMajor(major4));
    }

    @Test
    void test02UpdateMajor() {
        School school = new School();
        school.setSchoolId(1);

        Major major2 = new Major();
        major2.setMajorId(2);
        major2.setMajorName("major_2");
        major2.setIntroduction("major_2");
        major2.setSchool(school);
        assertSame(ResultMessage.SUCCESS, majorService.updateMajor(major2));
    }

    @Test
    void test03FindAllMajor() {
        List<Major> majorList = majorService.findAllMajor();
        assertNotNull(majorList);
        assertEquals(4, majorList.size());
        assertEquals(1, majorList.get(0).getMajorId());
        assertEquals(2, majorList.get(1).getMajorId());
        assertEquals(3, majorList.get(2).getMajorId());
        assertEquals(4, majorList.get(3).getMajorId());
    }

    @Test
    void test04FindMajorByMajorId() {
        Major major = majorService.findMajorByMajorId(1);
        assertNotNull(major);
        assertEquals(1, major.getMajorId());
        assertEquals("major_1", major.getMajorName());
        assertEquals("major_1", major.getIntroduction());
        assertEquals(1, major.getSchool().getSchoolId());
    }

    @Test
    void test04FindMajorByMajorName() {
        Major major = majorService.findMajorByMajorName("major_1");
        assertNotNull(major);
        assertEquals(1, major.getMajorId());
        assertEquals("major_1", major.getMajorName());
        assertEquals("major_1", major.getIntroduction());
        assertEquals(1, major.getSchool().getSchoolId());
    }

    @Test
    void test05DeleteMajor() {
        assertSame(ResultMessage.SUCCESS, majorService.deleteMajor(4));
    }
}
