package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.entity.Course;
import com.example.lab.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    @PostMapping(value = "/add")
    public ResultMessage addCourseApplication(Course course) {
        return applicationService.addCourseApplication(course);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResultMessage deleteCourseApplication(@PathVariable("courseId") Integer courseId) {
        return applicationService.deleteCourseApplication(courseId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateCourseApplication(Course course) {
        return applicationService.updateCourseApplication(course);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<Application>> findAllApplication() {
        Set<Application> applications = new HashSet<>(applicationService.findAllApplication());
        if (applications.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(applications ,HttpStatus.OK);
    }
}
