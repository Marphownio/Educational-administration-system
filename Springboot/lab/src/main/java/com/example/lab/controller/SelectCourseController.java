package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseService;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping(value = "course")
public class SelectCourseController {

    @Resource
    private UserService userService;

    @Resource
    private CourseService courseService;

    @PostMapping(value = "/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        if (admin.getCourseSelection()) {
            try {
                User currentUser = (User) session.getAttribute("user");

                System.out.println(currentUser.getCourses());

                Course course = courseService.findCourseByCourseId(courseId);

                System.out.println(course);
                System.out.println(course.getUsers());

                currentUser.getCourses().add(course);
//                course.getUsers().add(currentUser);

                System.out.println(currentUser.getCourses());
                System.out.println(course.getUsers());

                userService.updateUser(currentUser);
//                courseService.updateCourse(course);

                return ResultMessage.SUCCESS;
            } catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
        else {
            return ResultMessage.NOT_OPEN;
        }
    }

    @GetMapping(value = "/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        if (admin.getCourseSelection()) {
            User currentUser = (User) session.getAttribute("user");
            Set<Course> selectableCourses = currentUser.getMajor().getCourses();
            if (selectableCourses == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(selectableCourses, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
