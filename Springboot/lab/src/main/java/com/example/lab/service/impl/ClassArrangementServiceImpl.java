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

    @Override
    public ClassArrangement addClassArrangement(ClassArrangement classArrangement) {
        return classArrangementRepository.save(classArrangement);
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
    public ClassArrangement updateClassArrangement(ClassArrangement classArrangement) {
        return addClassArrangement(classArrangement);
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
