package com.example.lab.controller;

import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    // 管理员处理教师对课程的请求
    @DeleteMapping(value = "/teacher/application")
    public ResultMessage processCourseApplication(@RequestParam(value = "applicationId") Integer applicationId,
                                                  @RequestParam(value = "operation") Boolean operation) {
        return adminService.processCourseApplication(applicationId, operation);
    }

    // 管理员改变选课系统状态
    @PostMapping(value = "/courseSelect/next")
    public ResultMessage changeCourseSelectionStatus() {
        return adminService.changeCourseSelectionStatus();
    }

    // 获取当前选课系统状态
    @GetMapping(value = "/courseSelect/status")
    public CourseSelectionStatus getCourseSelectionSystem() {
        return admin.getCourseSelectionStatus();
    }

    // 设定学年学期
    @PutMapping(value = "/academicYearAndTerm/set")
    public ResultMessage setAcademicYearAndTerm(@RequestParam("academicYear") String academicYear, @RequestParam("term") String term) {
        try {
            admin.setAcademicYear(academicYear);
            admin.setTerm(term);
            return ResultMessage.SUCCESS;
        }
        catch (Exception e) {
            return ResultMessage.FAILED;
        }
    }

    // 获取当前学年学期
    @GetMapping(value = "/academicYearAndTerm")
    public String academicYearAndTerm() {
        return admin.getAcademicYear() + admin.getTerm();
    }
}
