package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/classroom")
public class ClassroomController {

    @Resource
    private ClassroomService classroomService;

    @PostMapping(value = "/add")
    public ResultMessage addClassroom(@RequestBody Classroom classroom){
        return classroomService.addClassroom(classroom);
    }

    @DeleteMapping(value = "/delete/{classroomId}")
    public ResultMessage deleteClassroom(@PathVariable("classroomId") Integer classroomId){
        return classroomService.deleteClassroom(classroomId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateClassroom(@RequestBody Classroom classroom){
        return classroomService.updateClassroom(classroom);
    }

    @GetMapping(value = "/list")
    public List<Classroom> findAllClassroom() {
        return classroomService.findAllClassroom();
    }

    @GetMapping(value = "/{classroomId}")
    public Classroom findClassroomById(@PathVariable("classroomId") Integer classroomId){
        return classroomService.findClassroomById(classroomId);
    }
}
