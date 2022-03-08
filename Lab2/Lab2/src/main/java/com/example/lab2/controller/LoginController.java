package com.example.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        if(username.equals("admin") && password.equals("123456")) {
            session.setAttribute("user", username);
            return "redirect:/homepage"; //重定向到主页
        }
        else {
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }
    }
}
