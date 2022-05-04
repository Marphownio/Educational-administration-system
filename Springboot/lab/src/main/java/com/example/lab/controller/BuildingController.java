package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.service.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/building")
public class BuildingController {

    @Resource
    private BuildingService buildingService;

    @PostMapping(value = "/add")
    public ResultMessage addBuilding(@RequestBody Building building){
        return buildingService.addBuilding(building);
    }

    @DeleteMapping(value = "/delete/{buildingId}")
    public ResultMessage deleteBuilding(@PathVariable("buildingId") Integer buildingId){
        return buildingService.deleteBuilding(buildingId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateBuilding(@RequestBody Building building){
        return buildingService.updateBuilding(building);
    }

    @GetMapping(value = "/list")
    public List<Building> findAllBuilding(){
        return buildingService.findAllBuilding();
    }

    @GetMapping(value = "/{buildingId}")
    public Building findBuildingById(@PathVariable("buildingId")Integer buildingId){
        return buildingService.findBuildingById(buildingId);
    }

    // 获取该教学楼下的所有教室
    @GetMapping(value = "/classrooms")
    public ResponseEntity<Set<Classroom>> findClassroomsInBuilding(@RequestParam(value = "buildingId") Integer buildingId) {
        Building building = buildingService.findBuildingById(buildingId);
        if (building == null || building.getClassrooms().isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(building.getClassrooms(), HttpStatus.OK);
    }
}
