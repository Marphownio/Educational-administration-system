package com.example.lab.service.impl;

import com.example.lab.LabApplication;
import com.example.lab.pojo.entity.Application;
import com.example.lab.pojo.ApplicationType;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.service.ApplicationService;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private CourseService courseService;

    @Resource
    private ApplicationRepository applicationRepository;

    @Override
    public ResultMessage addCourseApplication(Course course) {

        if (courseService.findCourseByCourseId(course.getCourseId()) != null) {
            return ResultMessage.EXIST;
        }
        else {
            Application application = new Application();

//            application.setApplicationId(course.getCourseId());

            application.setCourse(course);
            application.setType(ApplicationType.ADD);
            try {
                applicationRepository.save(application);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteCourseApplication(Integer courseId) {

        Course course = courseService.findCourseByCourseId(courseId);

        if (course == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            Application application = new Application();

//            application.setApplicationId(courseId);

            application.setCourse(course);
            application.setType(ApplicationType.DELETE);
            try {
                applicationRepository.save(application);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateCourseApplication(Course course) {

        if (courseService.findCourseByCourseId(course.getCourseId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            Application application = new Application();

//            application.setApplicationId(course.getCourseId());

            application.setCourse(course);
            application.setType(ApplicationType.UPDATE);
            try {
                applicationRepository.save(application);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<Application> findAllApplication() {
        return applicationRepository.findAll();
    }
}
