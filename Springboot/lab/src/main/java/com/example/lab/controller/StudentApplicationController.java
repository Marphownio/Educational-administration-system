package com.example.lab.controller;

import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.User;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.StudentApplication;
import com.example.lab.service.StudentApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/student/application")
public class StudentApplicationController {

    @Resource
    private StudentApplicationService studentApplicationService;

    @PostMapping(value = "/add")
    public ResultMessage addApplication(Integer courseId, Integer studentId, String reason){
        return studentApplicationService.addStudentApplication(courseId,studentId,reason);
    }

    @DeleteMapping(value = "/cancel")
    public ResultMessage cancelApplication(@RequestParam("applicationId") Integer applicationId){
        return studentApplicationService.deleteStudentApplication(applicationId);
    }

    @GetMapping(value = "/personallist")
    public ResponseEntity<Set<StudentApplication>> findStudentApplication(HttpSession session){
        User student = (Student)session.getAttribute("user");
        Set<StudentApplication> applications = new HashSet<>(studentApplicationService.findStudentApplication(student.getUserId()));
        if (applications.isEmpty()){
            return new ResponseEntity<>(new HashSet<>(),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(applications,HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Set<StudentApplication>> findAllApplication(){
        Set<StudentApplication> applications = new HashSet<>(studentApplicationService.findAllStudentApplication());
        if (applications.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(applications ,HttpStatus.OK);
    }
}
