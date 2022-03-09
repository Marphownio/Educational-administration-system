package com.example.lab2.controller;

import com.example.lab2.pojo.entity.User;
import com.example.lab2.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    //增加用户
    @PostMapping("")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    //删除用户
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
    }

    //修改用户
    @PutMapping("")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    //查询全部用户
    @GetMapping("")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    //通过id查询用户
    @GetMapping("/{id}")
    public User getUserbyId(@PathVariable("id") Integer id){
        return userService.getUserById(id);
    }
}