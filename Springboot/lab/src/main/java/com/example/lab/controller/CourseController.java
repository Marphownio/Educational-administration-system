package com.example.lab.controller;

import com.example.lab.pojo.Course;
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

    @PostMapping(value = "/add")
    public ResponseEntity<String> addCourse(Course course){
        switch (courseService.addCourse(course)){
            case EXIST:
                return new ResponseEntity<>("该课程代码已存在，添加失败！", HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>( "添加成功！",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("添加失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误！",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Integer courseId){
        switch (courseService.deleteCourse(courseId)){
            case NOTFOUND:
                return new ResponseEntity<>("课程不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("删除成功",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("删除失败",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateCourse(Course course){
        switch (courseService.updateCourse(course)){
            case NOTFOUND:
                return new ResponseEntity<>("课程不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("修改成功",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("修改失败",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping(value = "/list")
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
