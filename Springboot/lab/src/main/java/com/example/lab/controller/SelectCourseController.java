package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
}
