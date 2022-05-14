package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Admin;

public interface AdminService {

    // 设置管理员
    ResultMessage saveAdmin(Admin admin);

    // 获取管理员
    Admin getAdmin();

    // 设定学年学期
    ResultMessage setAcademicYearAndTerm(String academicYear, String term);

}
