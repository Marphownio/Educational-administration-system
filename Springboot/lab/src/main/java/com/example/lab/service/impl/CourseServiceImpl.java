package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Override
    public ResultMessage addCourse(Course course) {
        if (findCourseByCourseId(course.getCourseId()) != null) {
            return ResultMessage.EXIST;
        }
        else {
            try {
                courseRepository.save(course);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteCourse(Integer courseId) {
        if (findCourseByCourseId(courseId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                courseRepository.deleteById(courseId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateCourse(Course course) {
        if (findCourseByCourseId(course.getCourseId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                courseRepository.save(course);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }
}
