package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.entity.Classroom;
import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.entity.School;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private SchoolService schoolService;

    @Resource
    private MajorService majorService;

    @Resource
    private BuildingService buildingService;

    @Resource
    private ClassroomService classroomService;

    // 判断学院和专业是否匹配
    @Override
    public Boolean isMatchSchoolAndMajor(School school, Major major) {
        if (school == null || major == null) {
            return false;
        } else {
            School getSchool = schoolService.findSchoolBySchoolId(school.getSchoolId());
            Major getMajor = majorService.findMajorByMajorId(major.getMajorId());
            return getSchool != null && getMajor != null && Objects.equals(major.getSchool().getSchoolId(), school.getSchoolId());
        }
    }

    @Override
    public Boolean isMatchBuildingAndClassroom(Building building, Classroom classroom) {
        if (building == null || classroom == null){
            return false;
        }
        else {
            Building getBuilding = buildingService.findBuildingById(building.getBuildingId());
            Classroom getClassroom = classroomService.findClassroomById(classroom.getClassroomId());
            return getBuilding != null && getClassroom != null && Objects.equals(classroom.getBuilding().getBuildingId(),building.getBuildingId());
        }
    }
}
