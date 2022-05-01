package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.repository.BuildingRepository;
import com.example.lab.service.BuildingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Resource
    private BuildingRepository buildingRepository;

    @Override
    public ResultMessage addBuilding(Building building) {
        if (findBuildingById(building.getBuildingId())!= null){
            return ResultMessage.EXIST;
        }
        else {
            try {
                buildingRepository.save(building);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteBuilding(Integer buildingId) {
        if (findBuildingById(buildingId) == null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                buildingRepository.deleteById(buildingId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateBuilding(Building building) {
        if (findBuildingById(building.getBuildingId())==null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                buildingRepository.save(building);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<Building> findAllBuilding() {
        return buildingRepository.findAll();
    }

    @Override
    public Building findBuildingById(Integer buildingId) {
        return buildingRepository.findById(buildingId).orElse(null);
    }

}
