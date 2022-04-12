package com.example.lab.service;

import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;

public interface CommonService {

    // 判断学院和专业是否匹配
    Boolean isMatchSchoolAndMajor(School school, Major major);
//    Boolean isMatchBuildingAndClassroom(Building building, Classroom classroom);
}
