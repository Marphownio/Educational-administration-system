package com.example.lab.service.impl;

import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.repository.ClassArrangementRepository;
import com.example.lab.service.ClassArrangementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
    public ClassArrangement updateClassArrangement(ClassArrangement classArrangement) {
        return addClassArrangement(classArrangement);
    }

    @Override
    public List<ClassArrangement> findAllClassArrangement() {
        return classArrangementRepository.findAll();
    }

    @Override
    public ClassArrangement findClassArrangementById(Integer classArrangementId) {
        return classArrangementRepository.findById(classArrangementId).orElse(null);
    }

    @Override
    public Boolean isConflictArrangement(ClassArrangement classArrangement) {
        List<ClassArrangement> classArrangements = findAllClassArrangement();
        classArrangements.removeIf(classArrangement1 -> classArrangement1.getCourse() == null);
        for (ClassArrangement classArrangement1 : classArrangements) {
            if (!Objects.equals(classArrangement.getClassroom().getClassroomId(), classArrangement1.getClassroom().getClassroomId())
                    || classArrangement.getDayOfWeek() != classArrangement1.getDayOfWeek()) {
                continue;
            }
            for (ClassTime classTime : classArrangement.getClassTimes()) {
                for (ClassTime classTime1 : classArrangement1.getClassTimes()) {
                    if (Objects.equals(classTime1.getClassTimeId(), classTime.getClassTimeId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
