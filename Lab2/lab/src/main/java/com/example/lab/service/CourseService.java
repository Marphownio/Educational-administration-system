package com.example.lab.service;

import com.example.lab.pojo.Course;

import java.util.List;

public interface CourseService {

    //增加课程
    String addCourse(Course course);

    //删除课程
    String deleteCourse(Integer courseId);

    //修改课程
    String updateCourse(Course course);

    //查询全部专业
    List<Course> findAllCourse();

    //通过id查询专业
    Course findCourseByCourseId(Integer courseId);
    //通过名称查询专业
    Course findCourseByCourseName(String courseName);
}
