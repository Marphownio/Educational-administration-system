package com.example.lab.controller;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.pojo.entity.Major;
import com.example.lab.service.MajorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

// 专业的增删改查
@RestController
@RequestMapping(value = "/major")
public class MajorController {

    @Resource
    public MajorService majorService;

    @PostMapping(value = "/add")
    public ResultMessage addMajor(@RequestBody Major major){
        return majorService.addMajor(major);
    }

    @DeleteMapping(value = "/delete/{majorId}")
    public ResultMessage deleteMajor(@PathVariable("majorId") Integer majorId){
        return majorService.deleteMajor(majorId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateMajor(@RequestBody Major major){
        return majorService.updateMajor(major);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<Major>> findAllMajor(){
        Set<Major> majors = new HashSet<>(majorService.findAllMajor());
        if (majors.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(majors, HttpStatus.OK);
    }

    // 获取该专业开设的所有课程
    @GetMapping(value = "/courses")
    public ResponseEntity<Set<Course>> findCoursesInMajor(@RequestParam(value = "majorId") Integer majorId) {
        Major major = majorService.findMajorByMajorId(majorId);
        if (major == null || major.getCourseCategories().isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        Set<Course> courses = new HashSet<>();
        for (CourseCategory courseCategory : major.getCourseCategories()) {
            courses.addAll(courseCategory.getCourses());
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid/{majorId}")
    public ResponseEntity<Major> findMajorByMajorId(@PathVariable("majorId") Integer majorId){
        Major major = majorService.findMajorByMajorId(majorId);
        if (major == null) {
            return new ResponseEntity<>(new Major(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(major, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyname/{majorName}")
    public ResponseEntity<Major> findMajorByMajorName(@PathVariable("majorName") String majorName){
        Major major = majorService.findMajorByMajorName(majorName);
        if (major == null) {
            return new ResponseEntity<>(new Major(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(major, HttpStatus.OK);
    }
}


