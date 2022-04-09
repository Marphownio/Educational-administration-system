package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
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
    public ResultMessage addMajor(Major major){
        return majorService.addMajor(major);
    }

    @DeleteMapping(value = "/{majorId}")
    public ResultMessage deleteMajor(@PathVariable("majorId") Integer majorId){
        return majorService.deleteMajor(majorId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateMajor(Major major){
        return majorService.updateMajor(major);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<Major>> findAllMajor(){
        Set<Major> majors = new HashSet<>(majorService.findAllMajor());
        if (majors.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(majors, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid/{majorId}")
    public ResponseEntity<Major> findMajorByMajorId(@PathVariable("majorId") Integer majorId){
        Major major = majorService.findMajorByMajorId(majorId);
        if (major == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(major, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyname/{majorName}")
    public ResponseEntity<Major> findMajorByMajorName(@PathVariable("majorName") String majorName){
        Major major = majorService.findMajorByMajorName(majorName);
        if (major == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(major, HttpStatus.OK);
    }
}


