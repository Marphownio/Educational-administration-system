package com.example.lab.controller;


import com.example.lab.pojo.entity.Major;
import com.example.lab.service.MajorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/major")
public class MajorController {

    @Resource
    public MajorService majorService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addMajor(Major major){
        switch (majorService.addMajor(major)){
            case EXIST:
                return new ResponseEntity<>("该专业已存在，添加失败", HttpStatus.FORBIDDEN);
            case SUCCESS:
                return new ResponseEntity<>("添加成功！",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("添加失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping(value = "/{majorId}")
    public ResponseEntity<String> deleteMajor(@PathVariable("majorId") Integer majorId){
        switch (majorService.deleteMajor(majorId)){
            case NOTFOUND:
                return new ResponseEntity<>("该专业不存在，删除失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("删除成功！",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("删除失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateMajor(Major major){
        switch (majorService.updateMajor(major)){
            case NOTFOUND:
                return new ResponseEntity<>("专业不存在！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("修改成功！",HttpStatus.ACCEPTED);
            case FAILED:
                return new ResponseEntity<>("修改失败！",HttpStatus.ACCEPTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<Major>> findAllMajor(){
        Set<Major> majors = new HashSet<>(majorService.findAllMajor());
        if (majors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(majors,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{majorId}")
    public ResponseEntity<Major> findMajorByMajorId(@PathVariable("majorId") Integer majorId){
        Major major = majorService.findMajorByMajorId(majorId);
        if (major == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(major,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{majorName}")
    public ResponseEntity<Major> findMajorByMajorName(@PathVariable("majorName") String majorName){
        Major major = majorService.findMajorByMajorName(majorName);
        if (major == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(major,HttpStatus.ACCEPTED);
    }
}


