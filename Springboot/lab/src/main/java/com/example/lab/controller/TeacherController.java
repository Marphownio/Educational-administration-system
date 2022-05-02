package com.example.lab.controller;

import com.example.lab.pojo.entity.Teacher;
import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Resource
    private UserService userService;

    // 查询全部教师
    @GetMapping(value = "/list")
    public ResponseEntity<Set<Teacher>> findAllTeacher() {
        Set<Teacher> teachers = new HashSet<>(userService.findAllTeacher());
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // 查询指定教师发出的所有请求
    @GetMapping(value = "/application")
    public ResponseEntity<Set<TeacherApplication>> findAllTeacherApplication(@RequestParam(value = "teacherId") Integer teacherId) {
        Teacher teacher = userService.findTeacherByTeacherId(teacherId);
        if (teacher == null || teacher.getTeacherApplications().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(teacher.getTeacherApplications(), HttpStatus.OK);
        }
    }

}
