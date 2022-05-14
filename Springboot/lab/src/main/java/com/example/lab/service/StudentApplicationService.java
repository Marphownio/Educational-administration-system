package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.StudentApplication;

import java.util.List;

public interface StudentApplicationService {
    ResultMessage addStudentApplication(Integer courseId,Integer studentId,String reason);

    // 管理员处理完请求后将其删除
    ResultMessage deleteStudentApplication(Integer applicationId);

    // 管理员查询所有申请
    List<StudentApplication> findAllStudentApplication();

    // 学生查看自己的选课申请
    List<StudentApplication> findStudentApplication(Integer studentId);

    StudentApplication findStudentApplicationById(Integer applicationId);

    // 管理员处理学生对课程的请求
    ResultMessage processStudentApplication(Integer applicationId, Boolean operation);
}
