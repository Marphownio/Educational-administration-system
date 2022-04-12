package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;

import java.util.List;

public interface BuildingAndClassroomService {

    //教学楼
    ResultMessage addBuilding(Building building);

    ResultMessage deleteBuilding(Integer buildingId);

    ResultMessage updateBuilding(Building building);

    List<Building> findAllBuilding();

    Building findBuildingById(Integer buildingId);


    //教室
    ResultMessage addClassroom(Classroom classroom);

    ResultMessage deleteClassroom(Integer classroomId);

    ResultMessage updateClassroom(Classroom classroom);

    Classroom findClassroomById(Integer classroomId);

    List<Classroom> findAllClassroom();
}
