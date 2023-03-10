package com.example.lab.service.impl;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.repository.CourseCategoryRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (findCourseCategoryByCourseName(courseCategory.getCourseName()) != null) {
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
    public List<Course> findCourseByTermInCourseCategory(Integer courseCategoryId, String academicYear, String term) {
        CourseCategory courseCategory = findCourseCategoryByCourseCategoryId(courseCategoryId);
        if (courseCategory == null || courseCategory.getCourses().isEmpty()) {
            return Collections.emptyList();
        }
        List<Course> courses = new ArrayList<>();
        for (Course course : courseCategory.getCourses()) {
            if (Objects.equals(course.getAcademicYear(), academicYear) && Objects.equals(course.getTerm(), term)) {
                courses.add(course);
            }
        }
        return courses;
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
