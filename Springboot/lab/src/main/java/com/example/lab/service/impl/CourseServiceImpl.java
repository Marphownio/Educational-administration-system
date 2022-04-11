package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.CommonService;
import com.example.lab.service.CourseService;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

// 课程的增删改查服务
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private MajorService majorService;

    @Resource
    private SchoolService schoolService;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage addCourse(Course course) {
        if (findCourseByCourseId(course.getCourseId()) != null) {
            return ResultMessage.EXIST;
        } else if (course.getTeacher() == null || !commonService.isMatch(course.getSchool(), course.getMajor())) {
            return ResultMessage.FAILED;
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

    @Override
    public ResultMessage BatchImportCourse(MultipartFile file) {
        Course course = new Course();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            String line;
            while((line = reader.readLine())!= null){
                String []item = line.split(",");
                course.setCourseId(new Integer(item[0]));
                course.setCourseName(item[1]);
                course.setClassHour(new Integer(item[2]));
                course.setCredit(new Integer(item[3]));
                course.setClassTime(item[4]);
                course.setClassPlace(item[5]);
                course.setCapacity(item[6]);
                course.setIntroduction(item[7]);
                course.setMajor(majorService.findMajorByMajorName(item[8]));
                course.setSchool(schoolService.findSchoolBySchoolName(item[9]));
                courseRepository.save(course);
            }
        } catch (IOException e) {
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }
}
