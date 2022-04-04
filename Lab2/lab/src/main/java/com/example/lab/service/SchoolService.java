package com.example.lab.service;


import com.example.lab.pojo.School;

import java.util.List;

public interface SchoolService {
    //增加学院
    String addSchool(School school);

    //删除学院
    String deleteSchool(Integer schoolId);

    //更新学院信息
    String updateSchool(School school);
    //查找所有学院
    List<School> findAllSchool();

    //查找学院
    School findSchoolBySchoolId(Integer schoolId);

    School findSchoolBySchoolName(String schoolName);
    //查找学院下的开设课程？？？
}
