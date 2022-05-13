package com.example.lab.controller;

import com.example.lab.pojo.entity.*;
import com.example.lab.service.AdminService;
import com.example.lab.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private AdminService adminService;

    // 查询全部教师
    @GetMapping(value = "/list")
    public ResponseEntity<Set<Teacher>> findAllTeacher() {
        Set<Teacher> teachers = new HashSet<>(teacherService.findAllTeacher());
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // 查询指定教师发出的所有请求
    @GetMapping(value = "/application")
    public ResponseEntity<Set<TeacherApplication>> findAllTeacherApplication(HttpSession session) {
        User user = (User)session.getAttribute("user");
        Teacher teacher = teacherService.findTeacherByTeacherId(user.getUserId());
        if (teacher == null || teacher.getTeacherApplications().isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(teacher.getTeacherApplications(), HttpStatus.OK);
        }
    }

    // 当前学年指定教师的任课课程
    @GetMapping(value = "/courses")
    public ResponseEntity<Set<Course>> findAllCoursesTheTeacherTeaches(HttpSession session) {
        User user = (User)session.getAttribute("user");
        Teacher teacher = teacherService.findTeacherByTeacherId(user.getUserId());
        if (teacher == null) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        Set<Course> courses = new HashSet<>(teacher.getCourses());
        Admin admin = adminService.getAdmin();
        courses.removeIf(course -> Objects.equals(course.getAcademicYear(), admin.getAcademicYear()) && Objects.equals(course.getTerm(), admin.getTerm()));
        if (courses.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
