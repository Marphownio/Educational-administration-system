package com.example.lab.service;

import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.pojo.enums.ResultMessage;

import java.util.List;

public interface TeacherApplicationService {

    // 教师对增删改课程的申请
    ResultMessage addTeacherApplication(TeacherApplication application);

    ResultMessage applicationOfAddOrUpdateCourse(TeacherApplication application);

    ResultMessage applicationOfAddOrUpdateCourse1(TeacherApplication application);

    ResultMessage applicationOfAddOrUpdateCourse2(TeacherApplication application);

    void applicationOfAddOrUpdateCourseFailed(ResultMessage resultMessage2, TeacherApplication application);

    ResultMessage applicationOfDeleteCourse(TeacherApplication application);

    // 管理员处理完请求后将其删除
    ResultMessage deleteTeacherApplication(Integer applicationId);

    ResultMessage updateTeacherApplication(TeacherApplication application);

    // 管理员查询所有申请
    List<TeacherApplication> findAllTeacherApplication();

    // 通过id查询申请
    TeacherApplication findTeacherApplicationById(Integer applicationId);

    // 管理员处理教师对课程的请求
    ResultMessage processTeacherApplication(Integer applicationId, Boolean operation);
}
