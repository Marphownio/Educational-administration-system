package com.example.lab.service;

import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;

public interface CommonService {

    // 判断学院和专业是否匹配
    Boolean isMatch(School school, Major major);
}
