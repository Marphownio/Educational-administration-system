package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.School;

import java.util.List;

public interface SchoolService {

    // 增加学院
    ResultMessage addSchool(School school);

    // 删除学院
    ResultMessage deleteSchool(Integer schoolId);

    // 更新学院信息
    ResultMessage updateSchool(School school);

    // 查询所有学院
    List<School> findAllSchool();

    // 通过id查询学院
    School findSchoolBySchoolId(Integer schoolId);

    // 通过学院名查询学院
    School findSchoolBySchoolName(String schoolName);
}
