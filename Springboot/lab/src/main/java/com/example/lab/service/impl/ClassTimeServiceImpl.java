package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.repository.ClassTimeRepository;
import com.example.lab.service.ClassTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ClassTimeServiceImpl implements ClassTimeService {

    @Resource
    ClassTimeRepository classTimeRepository;

    @Override
    public ResultMessage addClassTime(ClassTime classTime) {
        if (findClassTimeById(classTime.getClassTimeId()) != null){
            return ResultMessage.EXIST;
        }
        else{
            try {
                classTimeRepository.save(classTime);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteClassTime(Integer classTimeId) {
        if (findClassTimeById(classTimeId) == null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                classTimeRepository.deleteById(classTimeId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateClassTime(ClassTime classTime) {
        if (findClassTimeById(classTime.getClassTimeId()) == null){
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                classTimeRepository.save(classTime);
                return ResultMessage.SUCCESS;
            }
            catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<ClassTime> findAllClassTime() {
        return classTimeRepository.findAll();
    }

    @Override
    public ClassTime findClassTimeById(Integer classTimeId) {
        return classTimeRepository.findById(classTimeId).orElse(null);
    }
}
