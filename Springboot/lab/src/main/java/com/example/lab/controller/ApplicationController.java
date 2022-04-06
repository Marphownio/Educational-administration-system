package com.example.lab.controller;

import com.example.lab.pojo.Application;
import com.example.lab.pojo.Course;
import com.example.lab.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;

/*    @PostMapping(value = "")
    public ResponseEntity<String> addCourseApplication(Application application){
        if (application == null){
            return new ResponseEntity<>("发出添加课程申请失败！", HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("发出课程添加申请成功！",HttpStatus.OK);
    }
*/
    @PostMapping
    public ResponseEntity<String> addCourseApplication(Course course){
        if (applicationService.addCourseApplication(course) == null){
            return new ResponseEntity<>("发出增加课程申请失败！",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("发出增加课程申请成功！",HttpStatus.OK);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<String> deleteCourseApplication(@PathVariable("courseId") Integer courseId){
        if (applicationService.deleteCourseApplication(courseId) == null){
            return new ResponseEntity<>("发出删除课程删除申请失败",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("发出课程删除申请成功！",HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateCourseApplication(Course course){
        if (applicationService.updateCourseApplication(course) == null){
            return new ResponseEntity<>("发出修改课程信息申请失败！",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("发出修改课程信息申请成功！",HttpStatus.OK);
    }

}
