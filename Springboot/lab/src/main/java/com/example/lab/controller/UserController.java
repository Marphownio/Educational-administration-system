package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.Teacher;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// 用户的增删改查
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 获取当前用户信息
    @GetMapping(value = "/info")
    public User getUserInfo(HttpSession session) {
        return  userService.findUserByUserId(((User)session.getAttribute("user")).getUserId());
    }

    @PostMapping(value = "/add")
    public ResultMessage addUser(User user) {
        return userService.addUser(user);
    }

//    @Resource
//    private ReadExcel readExcel;
//
    @PostMapping("/batchimport")
    public ResultMessage BatchImportUser(@RequestParam(value = "filename",required = false) MultipartFile file) {
        //判断文件是否为空
        if(file == null) return ResultMessage.NOTFOUND;
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if (name == null || ("").equals(name) && size == 0) return ResultMessage.NOTFOUND;
        //批量导入。参数：文件名，文件。
        ResultMessage resultMessage = userService.BatchImportUser(file);
        if(resultMessage == ResultMessage.SUCCESS){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }



    @DeleteMapping(value = "/{userId}")
    public ResultMessage deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateUser( User user) {
        return userService.updateUser(user);
    }

    // 查询全部用户
    @GetMapping(value = "/list")
    public ResponseEntity<Set<User>> findAllUser() {
        Set<User> users = new HashSet<>(userService.findAllUser());
        if (users.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 查询全部教师
    @GetMapping(value = "/teacher/list")
    public ResponseEntity<Set<Teacher>> findAllTeacher() {
        Set<Teacher> teachers = new HashSet<>(userService.findAllTeacher());
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // 查询全部学生
    @GetMapping(value = "/student/list")
    public ResponseEntity<Set<Student>> findAllStudent() {
        Set<Student> students = new HashSet<>(userService.findAllStudent());
        if (students.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 通过id查询用户
    @GetMapping(value = "/getbyid/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 通过姓名查询用户
    @GetMapping(value = "/getbyname/{username}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("username") String username) {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
