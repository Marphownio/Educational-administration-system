package com.example.lab.service;

import com.example.lab.pojo.Course;
import com.example.lab.pojo.ResultMessage;

public interface ApplicationService {

    // 教师申请增加课程
    ResultMessage addCourseApplication(Course course);

    // 教师申请删除课程
    ResultMessage deleteCourseApplication(Integer courseId);

    // 教师申请修改课程
    ResultMessage updateCourseApplication(Course course);
}
