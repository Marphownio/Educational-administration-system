package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class AddController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/add")
    public String add(@RequestParam("id") String id, @RequestParam("name") String name,
                      @RequestParam("idNumber") String idNumber,@RequestParam("phoneNumber") String phoneNumber,
                      @RequestParam("email") String email,Model model, HttpSession session){
        return "/add";
    }

}
