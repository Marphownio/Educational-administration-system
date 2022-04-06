package com.example.lab.service.impl;

import com.example.lab.pojo.Application;
import com.example.lab.service.AdminService;
import com.example.lab.service.CourseService;

import javax.annotation.Resource;

public class AdminServiceImpl implements AdminService {
    @Resource
    private CourseService courseService;

    @Override
    public String processCourseApplication(Application application) {
        try{
            switch (application.getType()){
                case "add":
                    courseService.addCourse(application.getCourse());
                    return "添加课程成功！";
                case "delete":
                    courseService.deleteCourse(application.getApplicationId());
                    return "删除课程成功！";
                case "update":
                    courseService.updateCourse(application.getCourse());
                    return "更新课程信息成功！";
            }
            return "发生错误！";
        }catch (Exception e){
            return "操作失败！";
        }
    }
}
