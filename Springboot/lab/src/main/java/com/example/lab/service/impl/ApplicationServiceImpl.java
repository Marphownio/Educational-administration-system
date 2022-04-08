package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.service.ApplicationService;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private CourseService courseService;

    @Resource
    private ApplicationRepository applicationRepository;

    @Override
    public ResultMessage addApplication(Application application) {
        if (application.getType() == null) {
            return ResultMessage.FAILED;
        }
        switch (application.getType()) {
            case ADD:
                if (courseService.findCourseByCourseId(application.getApplicationId()) != null) {
                    return ResultMessage.EXIST;
                }
                break;
            case DELETE:
            case UPDATE:
                if (courseService.findCourseByCourseId(application.getApplicationId()) == null) {
                    return ResultMessage.NOTFOUND;
                }
                break;
        }
        try {
            applicationRepository.save(application);
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception){
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage deleteApplication(Integer applicationId) {
        if (findApplicationById(applicationId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                applicationRepository.deleteById(applicationId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<Application> findAllApplication() {
        return applicationRepository.findAll();
    }

    @Override
    public Application findApplicationById(Integer applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }
}
