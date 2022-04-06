package com.example.lab.controller;


import com.example.lab.pojo.Major;
import com.example.lab.service.MajorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/major")
public class MajorController {
    @Resource
    public MajorService majorService;

    @PostMapping(value = "")
    public ResponseEntity<String> addMajor(Major major){
        switch (majorService.addMajor(major)){
            case "该专业存在，添加失败！":
                return new ResponseEntity<>("该专业已存在，添加失败", HttpStatus.FORBIDDEN);
            case "添加成功！":
                return new ResponseEntity<>("添加成功！",HttpStatus.ACCEPTED);
            case "添加失败！":
                return new ResponseEntity<>("添加失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{majorId}")
    public ResponseEntity<String> deleteMajor(@PathVariable("majorId") Integer majorId){
        switch (majorService.deleteMajor(majorId)){
            case "该专业不存在，删除失败！":
                return new ResponseEntity<>("该专业不存在，删除失败！",HttpStatus.BAD_REQUEST);
            case "删除成功！":
                return new ResponseEntity<>("删除成功！",HttpStatus.ACCEPTED);
            case "删除失败！":
                return new ResponseEntity<>("删除失败！",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("Hello World!",HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<String> updateMajor(Major major){
        switch (majorService.updateMajor(major)){
            case "专业不存在！":
                return new ResponseEntity<>("专业不存在！",HttpStatus.BAD_REQUEST);
            case "修改成功！":
                return new ResponseEntity<>("修改成功！",HttpStatus.ACCEPTED);
            case "修改失败！":
                return new ResponseEntity<>("修改失败！",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Hello World!",HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Major>> findAllMajor(){
        List<Major> majorList = new ArrayList<>(majorService.findAllMajor());
        if (majorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(majorList,HttpStatus.ACCEPTED);
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


