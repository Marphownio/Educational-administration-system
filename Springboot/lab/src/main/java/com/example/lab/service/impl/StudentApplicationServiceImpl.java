package com.example.lab.service.impl;

import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Admin;
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

    @Override
    public ResultMessage addStudentApplication(Integer courseId,Integer studentId,String reason) {
        Course course = courseService.findCourseByCourseId(courseId);
        Student student = studentService.findStudentByStudentId(studentId);
        StudentApplication studentApplication = new StudentApplication(course,student,reason);
        // 如果需要修改课程容量
        // 修改课程容量只有在学期开始与一轮选课期间是不需要考虑课程容量与已选人数的 其他阶段要修改容量都需要考虑
        Admin admin = adminService.getAdmin();
        if (course.getCapacity().equals(courseService.findCourseByCourseId(course.getCourseId()).getCapacity())
                && (admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST
                && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_TERM)
                && course.getCapacity() < course.getStudents().size()) {
            return ResultMessage.FAILED;
        }
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

    // 管理员处理学生对课程的请求
    @Override
    public ResultMessage processStudentApplication(Integer applicationId, Boolean operation) {
        if (Boolean.FALSE.equals(operation)) {
            return this.deleteStudentApplication(applicationId);
        }
        StudentApplication studentApplication = this.findStudentApplicationById(applicationId);
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (studentApplication != null) {
            try {
                Course course = studentApplication.getCourse();
                course.setCapacity(course.getCapacity() + 1);
                course.getStudents().add(studentApplication.getStudent());
                courseService.updateCourse(course);
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        } else {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }
}
