package com.example.lab.controller;


import com.example.lab.pojo.ResultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.lab.LabApplication.admin;


@RestController
@RequestMapping("/courseSelection")
public class CourseSelectionController {

    @PostMapping
    public ResponseEntity<ResultMessage> changeCourseSelection(@RequestParam("change") Boolean change){
        try{
            admin.setCourseSelection(change);
        }catch (Exception e){
            return new ResponseEntity<>(ResultMessage.FAILED,HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(ResultMessage.SUCCESS,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<ResultMessage> getCourseSelection(){
        if (admin.getCourseSelection()){
            return new ResponseEntity<>(ResultMessage.SUCCESS,HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultMessage.FAILED,HttpStatus.NOT_IMPLEMENTED);
    }
}
