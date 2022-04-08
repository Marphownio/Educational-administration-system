package com.example.lab.service;

import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.ResultMessage;

import java.util.List;

public interface ApplicationService {

    // 教师对增删改课程的申请
    ResultMessage addApplication(Application application);

    // 管理员处理完请求后将其删除
    ResultMessage deleteApplication(Integer applicationId);

    // 管理员查询所有申请
    List<Application> findAllApplication();

    Application findApplicationById(Integer applicationId);
}
