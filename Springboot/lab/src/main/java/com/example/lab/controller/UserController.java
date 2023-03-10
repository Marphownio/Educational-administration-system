package com.example.lab.controller;

import com.example.lab.pojo.entity.Admin;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.AdminService;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 用户的增删改查
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    // 登录
    @PostMapping(value = "/login")
    public ResultMessage login(@RequestParam(value = "loginid") String userId, @RequestParam(value = "loginpw") String password , HttpSession session) {
        return userService.login(userId, password, session);
    }

    // 注销
    @RequestMapping(value = "/logout")
    public ResultMessage logout(HttpSession session) {
        return userService.logout(session);
    }

    // 重置密码
    @RequestMapping(value ="/resetPassword")
    public ResultMessage resetPassword(@RequestParam("newpw1") String newPassword, HttpSession session) {
        return userService.resetPassword(newPassword, session);
    }

    // 获取当前用户信息
    @GetMapping(value = "/info")
    public User getUserInfo(HttpSession session) {
        if ((session.getAttribute("user")) instanceof Admin) {
            User user = new User();
            user.setUserId(adminService.getAdmin().getUserId());
            user.setRole(adminService.getAdmin().getRole());
            return user;
        }
        return  userService.findUserByUserId(((User)session.getAttribute("user")).getUserId());
    }

    @PostMapping(value = "/add")
    public ResultMessage addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 批量导入用户
    @PostMapping("/batchimport")
    public ResponseEntity<Map<String,String>> batchImportUser(@RequestParam(value = "file",required = false) MultipartFile file) {
        //判断文件是否为空
        HashMap<String,String> result = new HashMap<>();
        if(file == null) {
            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if (name == null || ("").equals(name) && size == 0) {
            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
        }
        //批量导入。参数：文件名，文件。
        try {
            result.putAll(userService.batchImportUser(file));
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (NumberFormatException e){
            return new ResponseEntity<>(result,HttpStatus.NOT_IMPLEMENTED);
        }
    }


    @DeleteMapping(value = "/delete/{userId}")
    public ResultMessage deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // 查询全部用户
    @GetMapping(value = "/list")
    public ResponseEntity<Set<User>> findAllUser() {
        Set<User> users = new HashSet<>(userService.findAllUser());
        if (users.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 通过id查询用户
    @GetMapping(value = "/getbyid/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 通过姓名查询用户
    @GetMapping(value = "/getbyname/{username}")
    public ResponseEntity<Set<User>> findUserByUserName(@PathVariable("username") String username) {
        Set<User> users = new HashSet<>(userService.findUserByUserName(username));
        if (users.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
