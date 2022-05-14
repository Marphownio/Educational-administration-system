package com.example.lab.service;

import com.example.lab.pojo.entity.*;

public interface CommonService {

    // 判断学院和专业是否匹配
    Boolean isMatchSchoolAndMajor(School school, Major major);

    // 判断教室与教学楼是否匹配
    Boolean isMatchBuildingAndClassroom(Building building, Classroom classroom);

}
