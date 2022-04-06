package com.example.lab.service.impl;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.Course;
import com.example.lab.pojo.User;
import com.example.lab.repository.ApplicationRepository;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseRepository courseRepository;

 /*   @Resource
    private ApplicationRepository applicationRepository;

    private HttpSession httpSession;
    private final User currentUser;
    {
        assert false;
        currentUser = (User)httpSession.getAttribute("user");
    }
*/

    //增加课程
    @Override
    public String addCourse(Course course) {
        String resultMsg;
        if (findCourseByCourseId(course.getCourseId()) != null) {
            resultMsg = "该课程代码已存在，添加失败！";
        }
        else {
            try {
                courseRepository.save(course);
                resultMsg = "添加成功！";
            }
            catch (Exception e) {
                resultMsg = "添加失败！";
            }
        }
        return resultMsg;
    }

    @Override
    public String deleteCourse(Integer courseId) {
        String resultMsg;
        if (findCourseByCourseId(courseId) == null) {
            resultMsg = "课程不存在";
        }
        else {
            try {
                courseRepository.deleteById(courseId);
                resultMsg = "删除成功";
            }
            catch (Exception e) {
                resultMsg = "删除失败";
            }
        }
        return resultMsg;
    }

    @Override
    public String updateCourse(Course course) {
        String resultMsg;
        if (findCourseByCourseId(course.getCourseId()) == null) {
            resultMsg = "课程不存在";
        }
        else {
            try {
                courseRepository.save(course);
                resultMsg = "修改成功";
            }
            catch (Exception e) {
                resultMsg = "修改失败";
            }
        }
        return resultMsg;
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
