package com.example.lab.service;

import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import com.example.lab.pojo.entity.User;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.enums.UserRole;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void test01AddUser() {
        School school = new School(); school.setSchoolId(1);
        Major major1 = new Major(); major1.setMajorId(1);
        Major major2 = new Major(); major2.setMajorId(2);
        Major major3 = new Major(); major3.setMajorId(3);

        User user0 = new User();
        user0.setUserId(20123456);
        user0.setRole(UserRole.TEACHER);
        user0.setSchool(school);
        user0.setUsername("teacher_1");
        user0.setIdNumber("123456789123456789");
        user0.setMajor(major1);
        user0.setStatus(true);

        User user1 = new User();
        user1.setUserId(200001);
        user1.setRole(UserRole.STUDENT);
        user1.setSchool(school);
        user1.setUsername("student_1");
        user1.setIdNumber("123456789123456781");
        user1.setMajor(major1);
        user1.setStatus(true);

        User user2 = new User();
        user2.setUserId(200002);
        user2.setRole(UserRole.STUDENT);
        user2.setSchool(school);
        user2.setUsername("student_2");
        user2.setIdNumber("123456789123456782");
        user2.setMajor(major2);
        user2.setStatus(true);

        User user3 = new User();
        user3.setUserId(200003);
        user3.setRole(UserRole.STUDENT);
        user3.setSchool(school);
        user3.setUsername("student_3");
        user3.setIdNumber("123456789123456783");
        user3.setMajor(major3);
        user3.setStatus(true);

        User user4 = new User();
        user4.setUserId(200004);
        user4.setRole(UserRole.STUDENT);
        user4.setSchool(school);
        user4.setUsername("student_4");
        user4.setIdNumber("123456789123456784");
        user4.setMajor(major3);
        user4.setStatus(true);

        assertSame(ResultMessage.SUCCESS, userService.addUser(user0));
        assertSame(ResultMessage.SUCCESS, userService.addUser(user1));
        assertSame(ResultMessage.SUCCESS, userService.addUser(user2));
        assertSame(ResultMessage.SUCCESS, userService.addUser(user3));
        assertSame(ResultMessage.SUCCESS, userService.addUser(user4));
    }

    @Test
    void test02UpdateUser() {
        School school = new School(); school.setSchoolId(1);
        Major major1 = new Major(); major1.setMajorId(1);
        User user4 = new User();
        user4.setUserId(200004);
        user4.setRole(UserRole.STUDENT);
        user4.setSchool(school);
        user4.setUsername("student_4");
        user4.setIdNumber("123456789123456784");
        user4.setMajor(major1);
        user4.setStatus(false);
        assertSame(ResultMessage.SUCCESS, userService.updateUser(user4));
    }

    @Test
    void test03FindAllUser() {
        List<User> userList = userService.findAllUser();
        assertNotNull(userList);
        assertEquals(5, userList.size());
    }

    @Test
    void test04FindUserByUserId() {
        User user = userService.findUserByUserId(200001);
        assertNotNull(user);
        assertEquals("student_1", user.getUsername());
    }

    @Test
    void test05DeleteUser() {
        assertSame(ResultMessage.SUCCESS, userService.deleteUser(200004));
    }

}
