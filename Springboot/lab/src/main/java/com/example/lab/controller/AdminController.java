package com.example.lab.controller;

import com.example.lab.pojo.Application;
import com.example.lab.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping(value = "")
    public ResponseEntity<String> processCourseApplication(Application application) {

        switch (adminService.processCourseApplication(application)) {
            case SUCCESS_ADD:
                return new ResponseEntity<>("添加课程成功！", HttpStatus.OK);
            case FAILED_ADD:
                return new ResponseEntity<>("添加课程失败！", HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS_DELETE:
                return new ResponseEntity<>("删除课程成功！",HttpStatus.OK);
            case FAILED_DELETE:
                return new ResponseEntity<>("删除课程失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS_UPDATE:
                return new ResponseEntity<>("修改课程成功！", HttpStatus.OK);
            case FAILED_UPDATE:
                return new ResponseEntity<>("修改课程失败！", HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误",HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
