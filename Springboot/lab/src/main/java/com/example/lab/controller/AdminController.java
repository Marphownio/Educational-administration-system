package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Application;
import com.example.lab.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping(value = "")
    public ResultMessage processCourseApplication(Application application) {
        return adminService.processCourseApplication(application);
    }
}
