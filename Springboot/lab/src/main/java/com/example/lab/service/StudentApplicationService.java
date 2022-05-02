package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.StudentApplication;

import java.util.List;

public interface StudentApplicationService {
    ResultMessage addStudentApplication(Integer courseId,Integer studentId,String reason);

    // 管理员处理完请求后将其删除
    ResultMessage deleteStudentApplication(Integer applicationId);

    // 管理员查询所有申请
    List<StudentApplication> findAllStudentApplication();

    StudentApplication findStudentApplicationById(Integer applicationId);
}
