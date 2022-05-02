package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;

import java.util.List;

public interface CourseCategoryService {

    // 增加一类课程
    ResultMessage addCourseCategory(CourseCategory courseCategory);

    // 删除一类课程
    ResultMessage deleteCourseCategory(Integer courseCategoryId);

    // 修改一类课程
    ResultMessage updateCourseCategory(CourseCategory courseCategory);

    // 查询全部种类的课程
    List<CourseCategory> findAllCourseCategory();

    // 查询一类课程中指定学年学期的课程
    List<Course> findCourseByTermInCourseCategory(CourseCategory courseCategory, String academicYear, String term);

    // 通过id查询一类课程
    CourseCategory findCourseCategoryByCourseCategoryId(Integer courseCategoryId);

    // 通过课程名查询该类课程
    CourseCategory findCourseCategoryByCourseName(String courseName);

}
