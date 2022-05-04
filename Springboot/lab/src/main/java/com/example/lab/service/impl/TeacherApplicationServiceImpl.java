package com.example.lab.service.impl;

import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.pojo.ResultMessage;
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
}
