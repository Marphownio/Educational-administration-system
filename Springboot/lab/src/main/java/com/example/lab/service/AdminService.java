package com.example.lab.service;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.ResultMessage;

public interface AdminService {

    // 处理教师对课程的请求
    ResultMessage processCourseApplication(Application application);
}