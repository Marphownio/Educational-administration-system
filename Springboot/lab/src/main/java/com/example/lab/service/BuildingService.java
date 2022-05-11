package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Building;

import java.util.List;

public interface BuildingService {

    ResultMessage addBuilding(Building building);

    ResultMessage deleteBuilding(Integer buildingId);

    ResultMessage updateBuilding(Building building);

    List<Building> findAllBuilding();

    Building findBuildingById(Integer buildingId);

}
