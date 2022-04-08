package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 获取用户信息
    @GetMapping(value = "/info")
    public User getUserInfo(HttpSession httpSession) {
        return  (User)httpSession.getAttribute("user");
    }

    @PostMapping(value = "/add")
    public ResultMessage addUser(User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/{userId}")
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
    public ResponseEntity<User> findUserByUserName(@PathVariable("username") String username) {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
