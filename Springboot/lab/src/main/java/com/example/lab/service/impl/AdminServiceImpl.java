package com.example.lab.service.impl;

import com.example.lab.pojo.ApplicationType;
import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private CourseService courseService;

    @Resource
    private ApplicationService applicationService;

    // 管理员处理教师对课程的请求
    @Override
    public ResultMessage processCourseApplication(Integer applicationId, Boolean operation) {

        if (!operation) {
            return applicationService.deleteApplication(applicationId);
        }
        Application application = applicationService.findApplicationById(applicationId);
        if (application == null) {
            return ResultMessage.FAILED;
        }
        Course course = new Course();
        if (application.getType() != ApplicationType.DELETE) {
            course.setCourseId(application.getApplicationId());
            course.setCourseName(application.getCourseName());
            course.setClassHour(application.getClassHour());
            course.setCredit(application.getCredit());
            course.setClassTime(application.getClassTime());
            course.setClassPlace(application.getClassPlace());
            course.setCapacity(application.getCapacity());
            course.setIntroduction(application.getIntroduction());
            course.setSchool(application.getSchool());
            course.setMajor(application.getMajor());
            course.setTeacher(application.getTeacher());
        }
        ResultMessage resultMessage;
        switch (application.getType()) {
            case ADD:
                resultMessage = courseService.addCourse(course); break;
            case DELETE:
                resultMessage = courseService.deleteCourse(application.getApplicationId()); break;
            case UPDATE:
                resultMessage = courseService.updateCourse(course); break;
            default:
                resultMessage = ResultMessage.FAILED; break;
        }
        if (resultMessage == ResultMessage.SUCCESS) {
            return applicationService.deleteApplication(applicationId);
        }
        else {
            return ResultMessage.FAILED;
        }
    }
}
