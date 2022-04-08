package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;

public interface AdminService {

    // 管理员处理教师对课程的请求
    ResultMessage processCourseApplication(Integer applicationId, Boolean operation);
}
