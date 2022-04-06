package com.example.lab.controller;

import com.example.lab.pojo.Application;
import com.example.lab.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<String> processCourseApplication(Application application){
        switch (adminService.processCourseApplication(application)){
            case "添加课程成功！":
                return new ResponseEntity<>("添加课程成功！", HttpStatus.OK);
            case "删除课程成功！":
                return new ResponseEntity<>("删除课程成功！",HttpStatus.OK);
            case "更新课程信息成功！":
                return new ResponseEntity<>("更新课程信息成功！",HttpStatus.OK);
            default:
                return new ResponseEntity<>("发声什么事了",HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
