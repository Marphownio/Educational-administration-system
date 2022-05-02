package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.repository.ClassArrangementRepository;
import com.example.lab.service.ClassArrangementService;
import com.example.lab.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassArrangementServiceImpl implements ClassArrangementService {
    @Resource
    private ClassArrangementRepository classArrangementRepository;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage addClassArrangement(ClassArrangement classArrangement) {
        if (findClassArrangementById(classArrangement.getClassArrangementId()) != null){
            return ResultMessage.EXIST;
        }
        else {
            if (Boolean.FALSE.equals(commonService.isMatchBuildingAndClassroom(classArrangement.getBuilding(),classArrangement.getClassroom()))){
                return ResultMessage.FAILED;
            }
            else {
                try {
                    classArrangementRepository.save(classArrangement);
                    return ResultMessage.SUCCESS;
                }
                catch (Exception e){
                    return ResultMessage.FAILED;
                }
            }
        }
    }

    @Override
    public ResultMessage deleteClassArrangement(Integer classArrangementId) {
        if (findClassArrangementById(classArrangementId) == null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                classArrangementRepository.deleteById(classArrangementId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateClassArrangement(ClassArrangement classArrangement) {
        // TODO
        return ResultMessage.FAILED;
    }

    @Override
    public List<ClassArrangement> findAllClassArrangement() {
        // TODO
        return new ArrayList<>();
    }

    @Override
    public ClassArrangement findClassArrangementById(Integer classArrangementId) {
        // TODO
        return null;
    }
}
