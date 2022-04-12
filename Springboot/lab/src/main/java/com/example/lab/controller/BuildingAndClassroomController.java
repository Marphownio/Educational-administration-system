package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.service.BuildingAndClassroomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/building")
public class BuildingAndClassroomController {
    @Resource
    private BuildingAndClassroomService buildingAndClassroomService;

    @PostMapping(value = "/addBuilding")
    public ResultMessage addBuilding(Building building){
        return buildingAndClassroomService.addBuilding(building);
    }

    @DeleteMapping(value = "/delete/{buildingId}")
    public ResultMessage deleteBuilding(@PathVariable("buildingId") Integer buildingId){
        return buildingAndClassroomService.deleteBuilding(buildingId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateBuilding(Building building){
        return buildingAndClassroomService.updateBuilding(building);
    }

    @GetMapping(value = "/list")
    public List<Building> findAllBuilding(){
        return buildingAndClassroomService.findAllBuilding();
    }

    @GetMapping(value = "/getbyid/{buildingId}")
    public Building findBuildingById(@PathVariable("buildingId")Integer buildingId){
        return buildingAndClassroomService.findBuildingById(buildingId);
    }


    //教室
    @PostMapping(value = "/addclassroom")
    public ResultMessage addClassroom(Classroom classroom){
        return buildingAndClassroomService.addClassroom(classroom);
    }

    @DeleteMapping(value = "/deleteclassroom/{classroomId}")
    public ResultMessage deleteClassroom(@PathVariable("classroomId") Integer classroomId){
        return buildingAndClassroomService.deleteClassroom(classroomId);
    }

    @PutMapping(value = "/updateclassroom")
    public ResultMessage updateClassroom(Classroom classroom){
        return buildingAndClassroomService.updateClassroom(classroom);
    }

    @GetMapping(value = "/classroomlist")
    public List<Classroom> findAllClassroom(){
        return buildingAndClassroomService.findAllClassroom();
    }

    @GetMapping(value = "/classroom/{classroomId}")
    public Classroom findClassroomById(@PathVariable("classroomId") Integer classroomId){
        return buildingAndClassroomService.findClassroomById(classroomId);
    }
}
