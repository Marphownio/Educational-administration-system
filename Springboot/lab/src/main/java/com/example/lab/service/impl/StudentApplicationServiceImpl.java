package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.StudentApplication;
import com.example.lab.repository.StudentApplicationRepository;
import com.example.lab.service.CourseService;
import com.example.lab.service.StudentApplicationService;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentApplicationServiceImpl implements StudentApplicationService {

    @Resource
    private StudentApplicationRepository studentApplicationRepository;

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    @Override
    public ResultMessage addStudentApplication(Integer courseId,Integer studentId,String reason) {
        Course course = courseService.findCourseByCourseId(courseId);
        Student student = userService.findStudentByStudentId(studentId);
        StudentApplication studentApplication = new StudentApplication(course,student,reason);
        try{
            studentApplicationRepository.save(studentApplication);
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage deleteStudentApplication(Integer applicationId) {
        if (findStudentApplicationById(applicationId) == null){
            return ResultMessage.NOTFOUND;
        }
        else{
            try {
                studentApplicationRepository.deleteById(applicationId);
                return ResultMessage.SUCCESS;
            }catch (Exception e){
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<StudentApplication> findAllStudentApplication() {
        return studentApplicationRepository.findAll();
    }

    @Override
    public StudentApplication findStudentApplicationById(Integer applicationId) {
        return studentApplicationRepository.findById(applicationId).orElse(null);
    }
}
