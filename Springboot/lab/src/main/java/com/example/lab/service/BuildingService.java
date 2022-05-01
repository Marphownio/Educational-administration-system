package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;

import java.util.List;

public interface BuildingService {

    ResultMessage addBuilding(Building building);

    ResultMessage deleteBuilding(Integer buildingId);

    ResultMessage updateBuilding(Building building);

    List<Building> findAllBuilding();

    Building findBuildingById(Integer buildingId);

}
