package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.repository.CourseCategoryRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

// 一类课程的增删改查服务
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Resource
    private CourseCategoryRepository courseCategoryRepository;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage addCourseCategory(CourseCategory courseCategory) {
        ResultMessage resultMessage;
        if (findCourseCategoryByCourseCategoryId(courseCategory.getCourseCategoryId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if (Boolean.FALSE.equals(commonService.isMatchSchoolAndMajor(courseCategory.getSchool(), courseCategory.getMajor()))) {
            resultMessage = ResultMessage.FAILED;
        } else {
            try {
                courseCategoryRepository.save(courseCategory);
                resultMessage = ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage deleteCourseCategory(Integer courseCategoryId) {
        if (findCourseCategoryByCourseCategoryId(courseCategoryId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                courseCategoryRepository.deleteById(courseCategoryId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateCourseCategory(CourseCategory courseCategory) {
        ResultMessage resultMessage;
        if (findCourseCategoryByCourseCategoryId(courseCategory.getCourseCategoryId()) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else if (Boolean.FALSE.equals(commonService.isMatchSchoolAndMajor(courseCategory.getSchool(), courseCategory.getMajor()))) {
            resultMessage = ResultMessage.FAILED;
        } else {
            try {
                courseCategoryRepository.save(courseCategory);
                resultMessage = ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public List<CourseCategory> findAllCourseCategory() {
        return courseCategoryRepository.findAll();
    }

    @Override
    public CourseCategory findCourseCategoryByCourseCategoryId(Integer courseId) {
        return courseCategoryRepository.findById(courseId).orElse(null);
    }

    @Override
    public CourseCategory findCourseCategoryByCourseName(String courseName) {
        return courseCategoryRepository.findByCourseName(courseName);
    }

}
