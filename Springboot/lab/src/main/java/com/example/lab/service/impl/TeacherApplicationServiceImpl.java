package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Admin;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.pojo.enums.ApplicationType;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.TeacherApplicationRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherApplicationServiceImpl implements TeacherApplicationService {

    @Resource
    private TeacherApplicationRepository teacherApplicationRepository;

    @Resource
    private CourseService courseService;

    @Resource
    private CommonService commonService;

    @Resource
    private AdminService adminService;

    private ResultMessage checkBeforeAddTeacherApplication(TeacherApplication application) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (application.getType() == null || !commonService.isMatchSchoolAndMajor(application.getCourseCategory().getSchool(), application.getCourseCategory().getMajor())) {
            resultMessage = ResultMessage.FAILED;
        } else {
            switch (application.getType()) {
                case ADD:
                    if (courseService.findCourseByCourseId(application.getCourseId()) != null) {
                        resultMessage = ResultMessage.EXIST;
                    }
                    break;
                case DELETE:
                case UPDATE:
                    if (courseService.findCourseByCourseId(application.getCourseId()) == null) {
                        resultMessage = ResultMessage.NOTFOUND;
                    }
                    break;
                default:
                    resultMessage = ResultMessage.FAILED;
                    break;
            }
        }
        return resultMessage;
    }

    // 教师申请增删改课程
    @Override
    public ResultMessage addTeacherApplication(TeacherApplication application) {
        ResultMessage resultMessage = checkBeforeAddTeacherApplication(application);
        if(resultMessage == ResultMessage.SUCCESS) {
            try {
                teacherApplicationRepository.save(application);
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    // 教师取消申请或管理员处理完申请后将其删除
    @Override
    public ResultMessage deleteTeacherApplication(Integer applicationId) {
        if (findTeacherApplicationById(applicationId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                teacherApplicationRepository.deleteById(applicationId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    // 管理员获取所有申请
    @Override
    public List<TeacherApplication> findAllTeacherApplication() {
        return teacherApplicationRepository.findAll();
    }

    // 通过id查找申请
    @Override
    public TeacherApplication findTeacherApplicationById(Integer applicationId) {
        return teacherApplicationRepository.findById(applicationId).orElse(null);
    }

    // 管理员处理教师对课程的请求
    @Override
    public ResultMessage processTeacherApplication(Integer applicationId, Boolean operation) {
        if (Boolean.FALSE.equals(operation)) {
            return this.deleteTeacherApplication(applicationId);
        }
        TeacherApplication application = this.findTeacherApplicationById(applicationId);
        if (application == null) {
            return ResultMessage.FAILED;
        }
        Course course = new Course();
        Admin admin = adminService.getAdmin();
        if (application.getType() != ApplicationType.DELETE) {
            course.setAcademicYear(admin.getAcademicYear());
            course.setTerm(admin.getTerm());
            course.setCapacity(application.getCapacity());
            course.setIntroduction(application.getIntroduction());
            course.setCourseCategory(application.getCourseCategory());
            course.setOpenToMajors(application.getOpenToMajors());
            course.setClassArrangements(application.getClassArrangements());
            course.setTeacher(application.getTeacher());
        }
        ResultMessage resultMessage;
        switch (application.getType()) {
            case ADD:
                resultMessage = courseService.addCourse(course); break;
            case DELETE:
                resultMessage = courseService.deleteCourse(application.getCourseId()); break;
            case UPDATE:
                resultMessage = courseService.updateCourse(course); break;
            default:
                resultMessage = ResultMessage.FAILED; break;
        }
        return (resultMessage == ResultMessage.SUCCESS) ? this.deleteTeacherApplication(applicationId) : ResultMessage.FAILED;
    }
}
