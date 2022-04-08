package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @DeleteMapping(value = "")
    public ResultMessage processCourseApplication(@RequestParam(value = "applicationId") Integer applicationId,
                                                  @RequestParam(value = "operation") Boolean operation) {
        return adminService.processCourseApplication(applicationId, operation);
    }
}
