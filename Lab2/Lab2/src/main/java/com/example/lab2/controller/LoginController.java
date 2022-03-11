package com.example.lab2.controller;

import com.example.lab2.pojo.User;
import com.example.lab2.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
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
