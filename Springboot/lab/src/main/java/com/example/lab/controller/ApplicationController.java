package com.example.lab.controller;

import com.example.lab.pojo.entity.Course;
import com.example.lab.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addCourseApplication(Course course){

        switch (applicationService.addCourseApplication(course)) {
            case EXIST:
                return new ResponseEntity<>("课程已存在，申请失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("增加课程申请发送成功！",HttpStatus.OK);
            case FAILED:
            default:
                return new ResponseEntity<>("未知错误！",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<String> deleteCourseApplication(@PathVariable("courseId") Integer courseId) {

        switch (applicationService.deleteCourseApplication(courseId)) {
            case NOTFOUND:
                return new ResponseEntity<>("课程不存在，申请失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("删除课程申请发送成功！",HttpStatus.OK);
            case FAILED:
            default:
                return new ResponseEntity<>("未知错误！",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateCourseApplication(Course course) {

        switch (applicationService.addCourseApplication(course)) {
            case EXIST:
                return new ResponseEntity<>("课程不存在，申请失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("修改课程申请发送成功！",HttpStatus.OK);
            case FAILED:
            default:
                return new ResponseEntity<>("未知错误！",HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
