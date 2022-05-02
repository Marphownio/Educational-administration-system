package com.example.lab.service;

import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    // 增加课程
    ResultMessage addCourse(Course course);

    // 删除课程
    ResultMessage deleteCourse(Integer courseId);

    // 修改课程
    ResultMessage updateCourse(Course course);

    // 查询全部课程
    List<Course> findAllCourse();

    // 查询指定指定学期的所有课程
    List<Course> findCourseByTerm(String academicYear, String term);

    // 通过id查询课程
    Course findCourseByCourseId(Integer courseId);

    //批量添加课程信息
    ResultMessage batchImportCourse(MultipartFile file);

}
