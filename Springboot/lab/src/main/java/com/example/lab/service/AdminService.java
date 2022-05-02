package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;

public interface AdminService {

    // 管理员处理教师对课程的请求
    ResultMessage processTeacherApplication(Integer applicationId, Boolean operation);

    ResultMessage processStudentApplication(Integer applicationId, Boolean operation);

    // 更改选课系统状态
    ResultMessage changeCourseSelectionStatus();

    ResultMessage firstScreening();
}
