package com.example.lab.controller;

import com.example.lab.pojo.enums.ApplicationStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.service.TeacherApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/teacher/application")
public class TeacherApplicationController {

    @Resource
    private TeacherApplicationService teacherApplicationService;

    // 增加申请
    @PostMapping(value = "/add")
    public ResultMessage addApplication(@RequestBody TeacherApplication application) {
        return teacherApplicationService.addTeacherApplication(application);
    }

    // 取消申请
    @DeleteMapping(value = "/cancel/{applicationId}")
    public ResultMessage cancelApplication(@PathVariable("applicationId") Integer applicationId) {
        return teacherApplicationService.deleteTeacherApplication(applicationId);
    }

    // 获取所有申请
    @GetMapping(value = "/list/all")
    public List<TeacherApplication> findAllTeacherApplication() {
        return teacherApplicationService.findAllTeacherApplication();
    }

    // 获取未审核的所有申请
    @GetMapping(value = "/list/inReview")
    public List<TeacherApplication> findAllTeacherApplicationInReview() {
        List<TeacherApplication> teacherApplications = teacherApplicationService.findAllTeacherApplication();
        teacherApplications.removeIf(application -> application.getStatus() != ApplicationStatus.IN_REVIEW);
        return teacherApplications;

    }

    // 获取已通过的所有申请
    @GetMapping(value = "/list/pass")
    public List<TeacherApplication> findAllTeacherApplicationPass() {
        List<TeacherApplication> teacherApplications = teacherApplicationService.findAllTeacherApplication();
        teacherApplications.removeIf(application -> application.getStatus() != ApplicationStatus.PASS);
        return teacherApplications;
    }

    // 获取未通过的所有申请
    @GetMapping(value = "/list/notPass")
    public List<TeacherApplication> findAllTeacherApplicationNotPass() {
        List<TeacherApplication> teacherApplications = teacherApplicationService.findAllTeacherApplication();
        teacherApplications.removeIf(application -> application.getStatus() != ApplicationStatus.NOT_PASS);
        return teacherApplications;
    }
}
