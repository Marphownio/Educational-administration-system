package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static com.example.lab.LabApplication.admin;

// 用户的增删改查
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 获取当前用户信息
    @GetMapping(value = "/info")
    public User getUserInfo(HttpSession session) {
        if ((session.getAttribute("user")).getClass().getSimpleName().equals("Admin")) {
            User user = new User();
            user.setUserId(admin.getUserId());
            user.setRole(admin.getRole());
            return user;
        }
        return  userService.findUserByUserId(((User)session.getAttribute("user")).getUserId());
    }

    @PostMapping(value = "/add")
    public ResultMessage addUser(User user) {
        return userService.addUser(user);
    }

    // 批量导入用户
    @PostMapping("/batchimport")
    public ResultMessage BatchImportUser(@RequestParam(value = "file",required = false) MultipartFile file) {
        //判断文件是否为空
        System.out.println(file);
        if(file == null) return ResultMessage.NOTFOUND;
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if (name == null || ("").equals(name) && size == 0) return ResultMessage.NOTFOUND;
        //批量导入。参数：文件名，文件。
        try {
            return userService.BatchImportUser(file);
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return ResultMessage.FAILED;
        }

    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResultMessage deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(value = "/update")
    public ResultMessage updateUser(User user) {
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
    public ResponseEntity<Set<User>> findUserByUserName(@PathVariable("username") String username) {
        Set<User> users = new HashSet<>(userService.findUserByUserName(username));
        if (users.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
