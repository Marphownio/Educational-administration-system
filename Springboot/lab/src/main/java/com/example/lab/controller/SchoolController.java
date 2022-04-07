package com.example.lab.controller;

import com.example.lab.pojo.entity.School;
import com.example.lab.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value="/school")
public class SchoolController {

    @Resource
    public SchoolService schoolService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addSchool(School school) {
        switch (schoolService.addSchool(school)){
            case EXIST:
                return new ResponseEntity<>("该学院已存在，添加失败！", HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("添加成功！",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("添加失败！",HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{schoolId}")
    public ResponseEntity<String> deleteSchool(@PathVariable("schoolId") Integer schoolId){
        switch (schoolService.deleteSchool(schoolId)){
            case NOTFOUND:
                return new ResponseEntity<>("学院不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("删除成功",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateSchool(School school){
        switch (schoolService.updateSchool(school)){
            case NOTFOUND:
                return new ResponseEntity<>("学院不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("修改成功",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("修改失败",HttpStatus.NOT_ACCEPTABLE);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbyid/{schoolId}")
    public ResponseEntity<School> findSchoolById(@PathVariable("schoolId") Integer schoolId){
        School school = schoolService.findSchoolBySchoolId(schoolId);
        if (school == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(school,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<School>> findAllSchool(){
        Set<School> schools = new HashSet<>(schoolService.findAllSchool());
        if(schools.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(schools,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getbyname/{schoolName}")
    public ResponseEntity<School> findSchoolBySchoolName(@PathVariable("schoolName") String schoolName){
        School school = schoolService.findSchoolBySchoolName(schoolName);
        if (school == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(school,HttpStatus.ACCEPTED);
    }
}
