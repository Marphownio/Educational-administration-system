package com.example.lab.service;

import com.example.lab.pojo.entity.Admin;
import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.enums.UserRole;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    void test01SaveAdmin() {
        Admin admin = new Admin(0,"fudan_admin", UserRole.ADMIN, CourseSelectionStatus.START_TERM,"2020-2021","2");
        assertSame(ResultMessage.SUCCESS, adminService.saveAdmin(admin));
    }

    @Test
    void test02GetAdmin() {
        Admin admin = adminService.getAdmin();
        assertEquals(0, admin.getUserId());
        assertEquals("fudan_admin", admin.getPassword());
        assertEquals(UserRole.ADMIN, admin.getRole());
        assertEquals(CourseSelectionStatus.START_TERM, admin.getCourseSelectionStatus());
        assertEquals("2020-2021", admin.getAcademicYear());
        assertEquals("2", admin.getTerm());
    }

    @Test
    void test03SetAcademicYearAndTerm() {
        assertSame(ResultMessage.SUCCESS, adminService.setAcademicYearAndTerm("2021-2022", "1"));
        assertEquals("2021-2022", adminService.getAdmin().getAcademicYear());
        assertEquals("1", adminService.getAdmin().getTerm());
    }
}
