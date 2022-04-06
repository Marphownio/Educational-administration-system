//package com.example.lab.controller;
//
//import com.example.lab.pojo.School;
//import com.example.lab.service.SchoolService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping(value="/school")
//public class SchoolController {
//    @Resource
//    public SchoolService schoolService;
//
//    @PostMapping(value = "")
//    public ResponseEntity<String> addSchool(School school) {
//        switch (schoolService.addSchool(school)){
//            case "该学院已存在，添加失败！":
//                return new ResponseEntity<>("该学院已存在，添加失败！", HttpStatus.CONFLICT);
//            case "添加成功！":
//                return new ResponseEntity<>("添加成功！",HttpStatus.ACCEPTED);
//            case "添加失败！":
//                return new ResponseEntity<>("添加失败！",HttpStatus.INTERNAL_SERVER_ERROR);
//            default:
//                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping(value = "/{schoolId}")
//    public ResponseEntity<String> deleteSchool(@PathVariable("schoolId") Integer schoolId){
//        switch (schoolService.deleteSchool(schoolId)){
//            case "学院不存在":
//                return new ResponseEntity<>("学院不存在",HttpStatus.BAD_REQUEST);
//            case "删除成功":
//                return new ResponseEntity<>("删除成功",HttpStatus.ACCEPTED);
//            case "删除失败":
//                return new ResponseEntity<>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
//            default:
//                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(value = "")
//    public ResponseEntity<String> updateSchool(School school){
//        switch (schoolService.updateSchool(school)){
//            case "学院不存在":
//                return new ResponseEntity<>("学院不存在",HttpStatus.BAD_REQUEST);
//            case "修改成功":
//                return new ResponseEntity<>("修改成功",HttpStatus.ACCEPTED);
//            case "修改失败":
//                return new ResponseEntity<>("修改失败",HttpStatus.INTERNAL_SERVER_ERROR);
//            default:
//                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping(value = "{/schoolId}")
//    public ResponseEntity<School> findSchoolById(@PathVariable("schoolId") Integer schoolId){
//        School school = schoolService.findSchoolBySchoolId(schoolId);
//        if (school == null){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(school,HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping(value = "")
//    public ResponseEntity<List<School>> findAllSchool(){
//        List<School> schoolList = new ArrayList<>(schoolService.findAllSchool());
//        if(schoolList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(schoolList,HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping(value = "/{schoolName}")
//    public ResponseEntity<School> findSchoolBySchoolName(@PathVariable("schoolName") String schoolName){
//        School school = schoolService.findSchoolBySchoolName(schoolName);
//        if (school == null){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(school,HttpStatus.ACCEPTED);
//    }
//}
