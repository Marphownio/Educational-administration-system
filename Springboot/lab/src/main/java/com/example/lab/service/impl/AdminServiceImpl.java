package com.example.lab.service.impl;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.service.AdminService;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private CourseService courseService;

    @Override
    public ResultMessage processCourseApplication(Application application) {

        switch (application.getType()){
            case ADD:
                if (courseService.addCourse(application.getCourse()) == ResultMessage.SUCCESS) {
                    return ResultMessage.SUCCESS_ADD;
                }
                else {
                    return ResultMessage.FAILED_ADD;
                }
            case DELETE:
                if (courseService.deleteCourse(application.getCourse().getCourseId()) == ResultMessage.SUCCESS) {
                    return ResultMessage.SUCCESS_DELETE;
                }
                else {
                    return ResultMessage.FAILED_DELETE;
                }
            case UPDATE:
                if (courseService.updateCourse(application.getCourse()) == ResultMessage.SUCCESS) {
                    return ResultMessage.SUCCESS_UPDATE;
                }
                else {
                    return ResultMessage.FAILED_UPDATE;
                }
            default:
                return ResultMessage.FAILED;
        }
    }
}
