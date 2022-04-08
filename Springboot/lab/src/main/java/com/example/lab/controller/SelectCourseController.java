package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Set;

@RestController
@RequestMapping(value = "course")
public class SelectCourseController {

    @Resource
    private CourseService courseService;

    @PostMapping(value = "/select")
    public ResultMessage selectCourse(Integer courseId, HttpSession session) {
        try {
            User currentUser =  (User)session.getAttribute("user");
            Course course = courseService.findCourseByCourseId(courseId);
            currentUser.getCourses().add(course);
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception) {
            return ResultMessage.FAILED;
        }
    }

    @GetMapping(value = "/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        User currentUser =  (User)session.getAttribute("user");
        Set<Course> courses = currentUser.getCourses();
        if (courses == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }
    }

}
