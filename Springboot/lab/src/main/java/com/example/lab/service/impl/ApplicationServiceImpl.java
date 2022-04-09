package com.example.lab.service.impl;

import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.service.ApplicationService;
import com.example.lab.service.CourseService;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    @Resource
    private ApplicationRepository applicationRepository;

    // 教师申请增删改课程
    @Override
    public ResultMessage addApplication(Application application) {
        User teacher = userService.findUserByUserId(application.getTeacherId());
        if (application.getType() == null || teacher == null || teacher.getRole() != UserRole.TEACHER) {
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
