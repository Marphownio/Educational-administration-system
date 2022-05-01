package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private CourseService courseService;

    @Resource
    private CommonService commonService;

    // 教师申请增删改课程
    @Override
    public ResultMessage addApplication(Application application) {
        ResultMessage resultMessage;
        if (application.getType() == null || !commonService.isMatchSchoolAndMajor(application.getCourseCategory().getSchool(), application.getCourseCategory().getMajor())) {
            resultMessage = ResultMessage.SUCCESS_LOGIN_STUDENT;
        } else {
            resultMessage = ResultMessage.SUCCESS;
            switch (application.getType()) {
                case ADD:
                    if (courseService.findCourseByCourseId(application.getCourseId()) != null) {
                        resultMessage = ResultMessage.EXIST;
                    }
                    break;
                case DELETE:
                case UPDATE:
                    if (courseService.findCourseByCourseId(application.getCourseId()) == null) {
                        resultMessage = ResultMessage.NOTFOUND;
                    }
                    break;
                default:
                    resultMessage = ResultMessage.FAILED; break;
            }
            if(resultMessage == ResultMessage.SUCCESS) {
                try {
                    applicationRepository.save(application);
                } catch (Exception exception) {
                    resultMessage = ResultMessage.FAILED;
                }
            }
        }
        return resultMessage;
    }

    // 教师取消申请或管理员处理完申请后将其删除
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

    // 管理员获取所有申请
    @Override
    public List<Application> findAllApplication() {
        return applicationRepository.findAll();
    }

    // 通过id查找申请
    @Override
    public Application findApplicationById(Integer applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }
}
