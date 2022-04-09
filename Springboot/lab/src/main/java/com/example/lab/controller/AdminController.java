package com.example.lab.controller;

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
    @DeleteMapping(value = "/application")
    public ResultMessage processCourseApplication(@RequestParam(value = "applicationId") Integer applicationId,
                                                  @RequestParam(value = "operation") Boolean operation) {
        return adminService.processCourseApplication(applicationId, operation);
    }

    // 管理员改变选课开关
    @PostMapping(value = "/courseSelect/change")
    public ResultMessage changeCourseSelection(@RequestParam("change") Boolean change) {
        try {
            admin.setCourseSelectionSystem(change);
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception) {
            return ResultMessage.FAILED;
        }
    }

    // 获取当前选课系统是否开启
    @GetMapping(value = "/courseSelect/status")
    public ResultMessage getCourseSelectionSystem() {
        if (admin.getCourseSelectionSystem()) {
            return ResultMessage.SUCCESS;
        }
        else {
            return ResultMessage.FAILED;
        }
    }
}
