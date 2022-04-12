package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.repository.BuildingRepository;
import com.example.lab.repository.ClassroomRepository;
import com.example.lab.service.BuildingAndClassroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BuildingAndClassroomServiceImpl implements BuildingAndClassroomService {
    @Resource
    BuildingRepository buildingRepository;

    @Resource
    ClassroomRepository classroomRepository;

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


    //教室
    @Override
    public ResultMessage addClassroom(Classroom classroom) {
        if (findClassroomById(classroom.getClassroomId())!=null){
            return ResultMessage.EXIST;
        }
        else {
            if (classroom.getBuilding() == null || findBuildingById(classroom.getBuilding().getBuildingId()) == null){
                return ResultMessage.FAILED;
            }
            else {
                try {
                    classroomRepository.save(classroom);
                    return ResultMessage.SUCCESS;
                }
                catch (Exception e) {
                    return ResultMessage.FAILED;
                }
            }
        }
    }

    @Override
    public ResultMessage deleteClassroom(Integer classroomId) {
        if (findClassroomById(classroomId)==null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                classroomRepository.deleteById(classroomId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateClassroom(Classroom classroom) {
        if (findClassroomById(classroom.getClassroomId())==null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                classroomRepository.save(classroom);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public Classroom findClassroomById(Integer classroomId) {
        return classroomRepository.findById(classroomId).orElse(null);
    }

    @Override
    public List<Classroom> findAllClassroom() {
        return classroomRepository.findAll();
    }
}
