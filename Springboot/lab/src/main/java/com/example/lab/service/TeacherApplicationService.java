package com.example.lab.service;

import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.pojo.enums.ResultMessage;

import java.util.List;

public interface TeacherApplicationService {

    // 教师对增删改课程的申请
    ResultMessage addTeacherApplication(TeacherApplication application);

    // 管理员处理完请求后将其删除
    ResultMessage deleteTeacherApplication(Integer applicationId);

    // 管理员查询所有申请
    List<TeacherApplication> findAllTeacherApplication();

    // 通过id查询申请
    TeacherApplication findTeacherApplicationById(Integer applicationId);

    // 管理员处理教师对课程的请求
    ResultMessage processTeacherApplication(Integer applicationId, Boolean operation);
}
