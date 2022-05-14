package com.example.lab.service.impl;

import com.example.lab.pojo.enums.ApplicationStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.StudentApplication;
import com.example.lab.repository.StudentApplicationRepository;
import com.example.lab.service.*;
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
    private StudentService studentService;

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Override
    public ResultMessage addStudentApplication(Integer courseId,Integer studentId,String reason) {
        Course course = courseService.findCourseByCourseId(courseId);
        Student student = studentService.findStudentByStudentId(studentId);
        StudentApplication studentApplication = new StudentApplication(course,student,reason);

        try{
            studentApplicationRepository.save(studentApplication);
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            return ResultMessage.FAILED;
        }
    }

    // 只有学生自己可以删除选课申请数据
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

    // 提供给管理员查看学生选课申请的接口
    @Override
    public List<StudentApplication> findAllStudentApplication() {
        return studentApplicationRepository.findAll();
    }

    // 学生查看自己的选课申请的接口
    @Override
    public List<StudentApplication> findStudentApplication(Integer studentId) {
        List<StudentApplication> result = this.findAllStudentApplication();
        result.removeIf(studentApplication -> !studentApplication.getStudent().getUserId().equals(studentId));

        return result;
    }

    // 通过课程id查找选课申请
    @Override
    public StudentApplication findStudentApplicationById(Integer applicationId) {
        return studentApplicationRepository.findById(applicationId).orElse(null);
    }

    // 管理员处理学生对课程的请求
    @Override
    public ResultMessage processStudentApplication(Integer applicationId, Boolean operation) {
        StudentApplication studentApplication = this.findStudentApplicationById(applicationId);
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (Boolean.FALSE.equals(operation)) {
            studentApplication.setApplicationStatus(ApplicationStatus.NOT_PASS);
            studentApplicationRepository.save(studentApplication);
            return resultMessage;
        }
        if (studentApplication != null) {
            try {
                Course course = studentApplication.getCourse();
                course.setCapacity(course.getCapacity() + 1);
                course.getStudents().add(studentApplication.getStudent());
                courseService.updateCourse(course);

                studentApplication.setApplicationStatus(ApplicationStatus.PASS);
                studentApplicationRepository.save(studentApplication);
                return  resultMessage;
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        } else {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }
}
