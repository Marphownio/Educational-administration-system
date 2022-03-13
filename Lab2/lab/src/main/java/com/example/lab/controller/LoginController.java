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
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/login")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model, HttpSession session) {
        User user = userService.findUserById(id);

        if (user == null || !password.equals(user.getPassword())) {
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }
        else {
            session.setAttribute("user", user);
            return "/index";
        }

    }
}