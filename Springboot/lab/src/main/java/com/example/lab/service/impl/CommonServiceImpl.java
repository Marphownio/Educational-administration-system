package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import com.example.lab.service.CommonService;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private SchoolService schoolService;

    @Resource
    private MajorService majorService;

    // 判断学院和专业是否匹配
    @Override
    public Boolean isMatch(School school, Major major) {
        if (school == null || major == null) {
            return false;
        } else {
            School getSchool = schoolService.findSchoolBySchoolId(school.getSchoolId());
            Major getMajor = majorService.findMajorByMajorId(major.getMajorId());
            return getSchool != null && getMajor != null && Objects.equals(major.getSchool().getSchoolId(), school.getSchoolId());
        }
    }
}
