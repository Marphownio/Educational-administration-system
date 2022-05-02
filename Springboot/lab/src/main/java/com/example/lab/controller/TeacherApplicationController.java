package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.TeacherApplication;
import com.example.lab.service.TeacherApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/teacher/application")
public class TeacherApplicationController {

    @Resource
    private TeacherApplicationService teacherApplicationService;

    // 增加申请
    @PostMapping(value = "/add")
    public ResultMessage addApplication(TeacherApplication application) {
        return teacherApplicationService.addTeacherApplication(application);
    }

    // 取消申请
    @DeleteMapping(value = "/cancel/{applicationId}")
    public ResultMessage cancelApplication(@PathVariable("applicationId") Integer applicationId) {
        return teacherApplicationService.deleteTeacherApplication(applicationId);
    }

    // 获取所有申请
    @GetMapping(value = "/list")
    public ResponseEntity<Set<TeacherApplication>> findAllApplication() {
        Set<TeacherApplication> applications = new HashSet<>(teacherApplicationService.findAllTeacherApplication());
        if (applications.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(applications ,HttpStatus.OK);
    }

}
