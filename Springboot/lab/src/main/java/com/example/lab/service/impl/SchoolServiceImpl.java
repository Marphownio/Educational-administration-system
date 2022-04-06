package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
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
    public ResultMessage addSchool(School school) {
        if (findSchoolBySchoolId(school.getSchoolId()) != null) {
            return ResultMessage.EXIST;
        }
        else {
            try {
                schoolRepository.save(school);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteSchool(Integer schoolId) {
        if (findSchoolBySchoolId(schoolId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                schoolRepository.deleteById(schoolId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateSchool(School school) {
        if (findSchoolBySchoolId(school.getSchoolId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                schoolRepository.save(school);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
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
