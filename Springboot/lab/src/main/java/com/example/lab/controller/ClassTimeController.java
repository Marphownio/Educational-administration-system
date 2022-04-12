package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.service.ClassTimeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/classTime")
public class ClassTimeController {
    @Resource
    private ClassTimeService classTimeService;

    @PostMapping(value = "/add")
    public ResultMessage addClassTime(ClassTime classTime){
        return classTimeService.addClassTime(classTime);
    }

    @DeleteMapping(value = "/delete/{classTimeId}")
    public ResultMessage deleteClassTime(@PathVariable("classTimeId") Integer classTimeId){
        return classTimeService.deleteClassTime(classTimeId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateClassTime(ClassTime classTime){
        return classTimeService.updateClassTime(classTime);
    }

    @GetMapping(value = "/list")
    public List<ClassTime> findAllClassTime(){
        return classTimeService.findAllClassTime();
    }

    @GetMapping(value = "/getbyid/{classTimeId}")
    public ClassTime findClassTimeById(@PathVariable("classTimeId")Integer classTimeId){
        return classTimeService.findClassTimeById(classTimeId);
    }
}
