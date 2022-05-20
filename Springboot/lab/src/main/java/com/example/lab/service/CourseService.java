package com.example.lab.service;

import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.enums.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface CourseService {

    Boolean isConflictTime(ClassArrangement classArrangement, ClassArrangement classArrangement1);

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

    // 增加或更新课程前，检查教师、课程安排、容量、教师与教学楼是否符合要求
    ResultMessage checkBeforeAddOrUpdateCourse(Course course);

    // 增加或更新课程前对课程安排和开放专业的准备
    ResultMessage prepareBeforeAddOrUpdateCourse1(Course course);

    // 增加或更新课程前对所属课程类的准备
    ResultMessage prepareBeforeAddOrUpdateCourse2(Course course);

    Boolean isConflictArrangement(ClassArrangement classArrangement);

    //批量添加课程信息
    HashMap<String,String> batchImportCourse(MultipartFile file);

}
