package com.example.lab.controller;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.*;
import com.example.lab.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    // 查询全部学生
    @GetMapping(value = "/list")
    public ResponseEntity<Set<Student>> findAllStudent() {
        Set<Student> students = new HashSet<>(studentService.findAllStudent());
        if (students.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 学生选课
    @PostMapping(value = "/course/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User student = (User) session.getAttribute("user");
        return studentService.selectCourse(student.getUserId(), courseId);
    }

    // 学生退课
    @DeleteMapping(value = "/course/drop")
    public ResultMessage dropCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        User student = (User) session.getAttribute("user");
        return studentService.dropCourse(student.getUserId(), courseId);
    }

    // 学生获取可选的课程
    @GetMapping(value = "/course/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        User student = (User) session.getAttribute("user");
        return new ResponseEntity<>(studentService.getSelectableCourse(student.getUserId()), HttpStatus.OK);
    }

    // 学生查看所有已修课程
    @GetMapping(value = "/courses/completed/all")
    public ResponseEntity<Set<Course>> findAllCoursesCompleted(HttpSession session) {
        User student = (User) session.getAttribute("user");
        return new ResponseEntity<>(studentService.findAllCoursesCompleted(student.getUserId()), HttpStatus.OK);
    }

    // 学生查看在指定学年学期的课程
    @GetMapping(value = "/courses/specified")
    public ResponseEntity<Set<Course>> findAllCoursesSpecified(@RequestParam(value = "academicYear") String academicYear,
                                                               @RequestParam(value = "term") String term, HttpSession session) {
        User student = (User) session.getAttribute("user");
        return new ResponseEntity<>(studentService.findAllCoursesSpecified(student.getUserId(), academicYear, term), HttpStatus.OK);
    }

    // 学生查看所有已选/在读课程
    @GetMapping(value = "/courses/studying")
    public ResponseEntity<Set<Course>> findAllCoursesStudying(HttpSession session) {
        User student = (User) session.getAttribute("user");
        return new ResponseEntity<>(studentService.findAllCoursesStudying(student.getUserId()), HttpStatus.OK);
    }
}
