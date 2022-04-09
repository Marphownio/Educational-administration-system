package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping(value = "/course")
public class SelectCourseController {

    @Resource
    private CourseService courseService;

    // 学生选课
    @PostMapping(value = "/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        if (admin.getCourseSelectionSystem()) {
            try {
                User currentUser = (User) session.getAttribute("user");
                Course course = courseService.findCourseByCourseId(courseId);
                course.getStudents().add(currentUser);
                courseService.updateCourse(course);
                return ResultMessage.SUCCESS;
            } catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
        else {
            return ResultMessage.NOT_OPEN;
        }
    }

    // 学生获取可选的课程
    @GetMapping(value = "/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        if (admin.getCourseSelectionSystem()) {
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
