package com.example.lab.controller;

import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private TeacherApplicationService teacherApplicationService;

    @Resource
    private StudentApplicationService studentApplicationService;

    @Resource
    private StudentService studentService;

    // 管理员处理教师对课程的请求
    @RequestMapping(value = "/application/teacher")
    public ResultMessage processCourseApplication(@RequestParam(value = "applicationId") Integer applicationId,
                                                  @RequestParam(value = "operation") Boolean operation) {
        return teacherApplicationService.processTeacherApplication(applicationId, operation);
    }

    // 管理员处理学生对课程的请求
    @RequestMapping(value = "/application/student")
    public ResultMessage processStudentApplication(@RequestParam(value = "applicationId") Integer applicationId,
                                                   @RequestParam(value = "operation") Boolean operation){
        return studentApplicationService.processStudentApplication(applicationId,operation);
    }

    // 改变选课系统状态
    @PostMapping(value = "/courseSelect/next")
    public ResultMessage changeCourseSelectionStatus() {
        return studentService.changeCourseSelectionStatus();
    }

    // 获取当前选课系统状态
    @GetMapping(value = "/courseSelect/status")
    public CourseSelectionStatus getCourseSelectionSystem() {
        return adminService.getAdmin().getCourseSelectionStatus();
    }

    // 设定学年学期
    @PutMapping(value = "/academicYearAndTerm/set")
    public ResultMessage setAcademicYearAndTerm(@RequestParam("academicYear") String academicYear, @RequestParam("term") String term) {
        return adminService.setAcademicYearAndTerm(academicYear, term);
    }

    // 获取当前学年学期
    @GetMapping(value = "/academicYearAndTerm")
    public String academicYearAndTerm() {
        return adminService.getAdmin().getAcademicYear() + adminService.getAdmin().getTerm();
    }
}
