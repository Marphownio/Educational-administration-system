package com.example.lab.service;

import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.ResultMessage;

import java.util.List;
import java.util.Set;

public interface ApplicationService {

    // 教师申请增加课程
    ResultMessage addCourseApplication(Course course);

    // 教师申请删除课程
    ResultMessage deleteCourseApplication(Integer courseId);

    // 教师申请修改课程
    ResultMessage updateCourseApplication(Course course);

    // 管理员查询所有申请
    List<Application> findAllApplication();
}
