package com.example.lab.service.impl;

import com.example.lab.pojo.School;
import com.example.lab.repository.SchoolRepository;
import com.example.lab.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SchoolServiceImpl implements SchoolService {

    @Resource
    private SchoolRepository schoolRepository;
    @Override
    public String addSchool(School school) {
        String resultMsg;
        if (findSchoolBySchoolId(school.getSchoolId()) != null) {
            resultMsg = "该学院已存在，添加失败！";
        }
        else {
            try {
                schoolRepository.save(school);
                resultMsg = "添加成功！";
            }
            catch (Exception e) {
                resultMsg = "该学院已存在，添加失败！";
            }
        }
        return resultMsg;
    }

    @Override
    public String deleteSchool(Integer schoolId) {
        String resultMsg;
        if (findSchoolBySchoolId(schoolId) == null) {
            resultMsg = "学院不存在";
        }
        else {
            try {
                schoolRepository.deleteById(schoolId);
                resultMsg = "删除成功";
            }
            catch (Exception e) {
                resultMsg = "删除失败";
            }
        }
        return resultMsg;
    }

    @Override
    public String updateSchool(School school) {
        String resultMsg;
        if (findSchoolBySchoolId(school.getSchoolId()) == null) {
            resultMsg = "学院不存在";
        }
        else {
            try {
                schoolRepository.save(school);
                resultMsg = "修改成功";
            }
            catch (Exception e) {
                resultMsg = "修改失败";
            }
        }
        return resultMsg;
    }

    @Override
    public List<School> findAllSchool() {
        return schoolRepository.findAll();
    }

    @Override
    public School findSchoolBySchoolId(Integer schoolId) {
        return schoolRepository.findById(schoolId).orElse(null);
    }

    @Override
    public School findSchoolBySchoolName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName);
    }

}
