package com.example.lab.service.impl;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.repository.ClassArrangementRepository;
import com.example.lab.service.ClassArrangementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassArrangementServiceImpl implements ClassArrangementService {

    @Resource
    private ClassArrangementRepository classArrangementRepository;

    @Override
    public ClassArrangement addClassArrangement(ClassArrangement classArrangement) {
        return classArrangementRepository.save(classArrangement);
    }

    @Override
    public ResultMessage deleteClassArrangement(Integer classArrangementId) {
        try {
            classArrangementRepository.deleteById(classArrangementId);
            return ResultMessage.SUCCESS;
        }
        catch (Exception e){
            return ResultMessage.FAILED;
        }
    }

    @Override
    public List<ClassArrangement> findAllClassArrangement() {
        return classArrangementRepository.findAll();
    }

}
