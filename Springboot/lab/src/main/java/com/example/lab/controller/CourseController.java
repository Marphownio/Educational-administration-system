package com.example.lab.controller;

import com.example.lab.pojo.Course;
import com.example.lab.service.AdminService;
import com.example.lab.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/course")
public class CourseController {

    @Resource
    private CourseService courseService;
    private AdminService adminService;

    @PostMapping(value = "")
    public ResponseEntity<String> addCourse(Course course){
        switch (courseService.addCourse(course)){
            case "该课程代码已存在，添加失败！":
                return new ResponseEntity<>("该课程代码已存在，添加失败！", HttpStatus.NOT_IMPLEMENTED);
            case "添加成功！":
                return new ResponseEntity<>( "添加成功！",HttpStatus.OK);
            case "添加失败！":
                return new ResponseEntity<>("添加失败！",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Integer courseId){
        switch (courseService.deleteCourse(courseId)){
            case "课程不存在":
                return new ResponseEntity<>("课程不存在",HttpStatus.NOT_IMPLEMENTED);
            case "删除成功":
                return new ResponseEntity<>("删除成功",HttpStatus.OK);
            case "删除失败":
                return new ResponseEntity<>("删除失败",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateCourse(Course course){
        switch (courseService.updateCourse(course)){
            case "课程不存在":
                return new ResponseEntity<>("课程不存在",HttpStatus.NOT_IMPLEMENTED);
            case "修改成功":
                return new ResponseEntity<>("修改成功",HttpStatus.OK);
            case "修改失败":
                return new ResponseEntity<>("修改失败",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAllCourse(){
        List<Course> courseList = new ArrayList<>(courseService.findAllCourse());
        if (courseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<Course> findCourseByCourseId(@PathVariable("courseId") Integer courseId){
        Course course = courseService.findCourseByCourseId(courseId);
        if (course == null){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{courseName}")
    public ResponseEntity<Course> findCourseByCourseName(@PathVariable("courseName") String courseName){
        Course course = courseService.findCourseByCourseName(courseName);
        if (course == null){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
