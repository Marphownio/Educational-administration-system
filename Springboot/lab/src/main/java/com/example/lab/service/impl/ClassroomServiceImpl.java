package com.example.lab.service.impl;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.repository.ClassroomRepository;
import com.example.lab.service.BuildingService;
import com.example.lab.service.ClassroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Resource
    private ClassroomRepository classroomRepository;

    @Resource
    private BuildingService buildingService;

    //教室
    @Override
    public ResultMessage addClassroom(Classroom classroom) {
        ResultMessage resultMessage;
        if (findClassroomById(classroom.getClassroomId())!=null){
            resultMessage = ResultMessage.EXIST;
        } else if (classroom.getBuilding() != null || buildingService.findBuildingById(classroom.getBuilding().getBuildingId()) != null){
            try {
                classroomRepository.save(classroom);
                resultMessage = ResultMessage.SUCCESS;
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        } else {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
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
