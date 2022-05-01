package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashSet;
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
    public ResponseEntity<Set<Course>> findAllCourse() {
        Set<Course> courseList = new HashSet<>(courseService.findAllCourse());
        if (courseList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseList ,HttpStatus.OK);
    }

    // 获取该课程下的所有学生
    @GetMapping(value = "/students")
    public ResponseEntity<Set<Student>> findStudentsInCourse(@RequestParam(value = "courseId") Integer courseId) {
        Course course = courseService.findCourseByCourseId(courseId);
        if (course == null || course.getStudents().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course.getStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<Course> findCourseByCourseId(@PathVariable("courseId") Integer courseId){
        Course course = courseService.findCourseByCourseId(courseId);
        if (course == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("/batchimport")
    public ResultMessage BatchImportCourse(@RequestParam(value = "file",required = false) MultipartFile file) {
        //判断文件是否为空
        if(file == null) return ResultMessage.NOTFOUND;
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if (name == null || ("").equals(name) && size == 0) return ResultMessage.NOTFOUND;
        //批量导入。参数：文件名，文件。
        ResultMessage resultMessage = courseService.BatchImportCourse(file);
        if(resultMessage == ResultMessage.SUCCESS){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }
}
