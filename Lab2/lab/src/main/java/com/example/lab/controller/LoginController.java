package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Resource
    public UserService userService;

    //登录
    @RequestMapping(value = "/login")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model, HttpSession session) {

        User user = userService.findUserById(id);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }
        else {
            model.addAttribute("id", id);
            session.setAttribute("user", user);
            // 初次登录，需要重置密码
            if (password.equals("123456")) {
                return "/resetPassword";
            }
            // 跳转到首页
            return "redirect:/index";
        }
    }
}
