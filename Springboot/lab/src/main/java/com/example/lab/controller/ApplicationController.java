package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Application;
import com.example.lab.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    @PostMapping(value = "/add")
    public ResultMessage addApplication(Application application) {
        return applicationService.addApplication(application);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<Application>> findAllApplication() {
        Set<Application> applications = new HashSet<>(applicationService.findAllApplication());
        if (applications.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(applications ,HttpStatus.OK);
    }
}
