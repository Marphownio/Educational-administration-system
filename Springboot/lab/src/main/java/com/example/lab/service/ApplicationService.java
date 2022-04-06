package com.example.lab.service;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.Course;

public interface ApplicationService {
    Application addCourseApplication(Course course);
    Application deleteCourseApplication(Integer courseId);
    Application updateCourseApplication(Course course);
}
