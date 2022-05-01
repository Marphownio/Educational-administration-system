package com.example.lab.controller;

import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.service.CourseCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/courseCategory")
public class CourseCategoryController {

    @Resource
    private CourseCategoryService courseCategoryService;

    @GetMapping(value = "/list")
    public ResponseEntity<Set<CourseCategory>> findAllCourseCategories(){
        Set<CourseCategory> courseCategories = new HashSet<>(courseCategoryService.findAllCourseCategory());
        if (courseCategories.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseCategories ,HttpStatus.OK);
    }
}
