package com.example.lab.controller;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import com.example.lab.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

// 学院的增删改查
@RestController
@RequestMapping(value="/school")
public class SchoolController {

    @Resource
    public SchoolService schoolService;

    @PostMapping(value = "/add")
    public ResultMessage addMajor(@RequestBody School school){
        return schoolService.addSchool(school);
    }

    @DeleteMapping(value = "/delete/{schoolId}")
    public ResultMessage deleteMajor(@PathVariable("schoolId") Integer schoolId) {
        return schoolService.deleteSchool(schoolId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateMajor(@RequestBody School school){
        return schoolService.updateSchool(school);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<School>> findAllSchool() {
        Set<School> schools = new HashSet<>(schoolService.findAllSchool());
        if (schools.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }

    // 获取该学院下的所有专业
    @GetMapping(value = "/majors")
    public ResponseEntity<Set<Major>> findMajorsInSchool(@RequestParam(value = "schoolId") Integer schoolId) {
        School school = schoolService.findSchoolBySchoolId(schoolId);
        if (school == null || school.getMajors().isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(school.getMajors(), HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid/{schoolId}")
    public ResponseEntity<School> findSchoolById(@PathVariable("schoolId") Integer schoolId){
        School school = schoolService.findSchoolBySchoolId(schoolId);
        if (school == null) {
            return new ResponseEntity<>(new School(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyname/{schoolName}")
    public ResponseEntity<School> findSchoolBySchoolName(@PathVariable("schoolName") String schoolName){
        School school = schoolService.findSchoolBySchoolName(schoolName);
        if (school == null) {
            return new ResponseEntity<>(new School(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(school, HttpStatus.OK);
    }
}
