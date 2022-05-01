package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseService;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping(value = "/course")
public class SelectCourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    // 学生选课
    @PostMapping(value = "/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        if (Boolean.TRUE.equals(admin.getCourseSelectionSystem())) {
            try {
                Student currentUser = userService.findStudentByStudentId(((Student) session.getAttribute("user")).getUserId());
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
        if (Boolean.TRUE.equals(admin.getCourseSelectionSystem() && session.getAttribute("user") != null) && ((User)session.getAttribute("user")).getRole() == UserRole.STUDENT) {
            Student currentUser =  userService.findStudentByStudentId(((User)session.getAttribute("user")).getUserId());

            Set<Course> selectableCourses = new HashSet<>();

            for (CourseCategory courseCategory : currentUser.getMajor().getSelectableCourseCategories()) {
                selectableCourses.addAll(courseCategory.getCourses());
            }

            if (selectableCourses.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                selectableCourses.removeIf(course -> !currentUser.getCourses().contains(course));
                if (selectableCourses.isEmpty()) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(selectableCourses, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
