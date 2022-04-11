package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// 课程的增删改查
@RestController
@RequestMapping(value = "/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping(value = "/add")
    public ResultMessage addCourse(Course course){
        return courseService.addCourse(course);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResultMessage deleteCourse(@PathVariable("courseId") Integer courseId){
        return courseService.deleteCourse(courseId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateCourse(Course course){
        return courseService.updateCourse(course);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Course>> findAllCourse(){
        List<Course> courseList = new ArrayList<>(courseService.findAllCourse());
        if (courseList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseList ,HttpStatus.OK);
    }

    // 获取该课程下的所有学生
    @GetMapping(value = "/students")
    public ResponseEntity<Set<Student>> findStudentsInCourse(@RequestParam(value = "courseId") Integer courseId) {
        Course course = courseService.findCourseByCourseId(courseId);
        if (course == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else if (course.getStudents().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course.getStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid/{courseId}")
    public ResponseEntity<Course> findCourseByCourseId(@PathVariable("courseId") Integer courseId){
        Course course = courseService.findCourseByCourseId(courseId);
        if (course == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyname/{courseName}")
    public ResponseEntity<Course> findCourseByCourseName(@PathVariable("courseName") String courseName){
        Course course = courseService.findCourseByCourseName(courseName);
        if (course == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
