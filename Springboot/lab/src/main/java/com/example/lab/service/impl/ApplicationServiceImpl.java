package com.example.lab.service.impl;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.Course;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.service.ApplicationService;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Resource
    private CourseService courseService;
    @Resource
    private ApplicationRepository applicationRepository;

    @Override
    public Application addCourseApplication(Course course) {
        if (courseService.findCourseByCourseId(course.getCourseId()) != null) {
            return null;
        }
        Application application = null;
        application.setApplicationId(course.getCourseId());
        application.setCourse(course);
        application.setType("add");
        try{
            applicationRepository.save(application);
        }catch (Exception e){
            return null;
        }
        return application;
    }

    @Override
    public Application deleteCourseApplication(Integer courseId) {
        if (courseService.findCourseByCourseId(courseId) == null) {
            return null;
        }
        Application application = null;
        application.setApplicationId(courseId);
        application.setCourse(courseService.findCourseByCourseId(courseId));
        application.setType("delete");
        try{
            applicationRepository.save(application);
        }catch (Exception e){
            return null;
        }
        return application;
    }

    @Override
    public Application updateCourseApplication(Course course) {
        if (course.getCourseId() == null) {
            return null;
        }
        Application application = null;
        application.setApplicationId(course.getCourseId());
        application.setCourse(course);
        application.setType("update");
        try{
            applicationRepository.save(application);
        }catch (Exception e){
            return null;
        }
        return application;
    }
}
