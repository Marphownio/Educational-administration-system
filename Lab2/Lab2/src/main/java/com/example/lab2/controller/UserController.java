package com.example.lab2.controller;

import com.example.lab2.mapper.UserMapper;
import com.example.lab2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/list")
    public String UserList(Module model){
        Collection<User> allUsers = userMapper.getAllUsers();
        model.addAttribute("emps", allUsers);
        return "/list";
    }

    @RequestMapping("/addUser")
    public void addUser() {

    }
}