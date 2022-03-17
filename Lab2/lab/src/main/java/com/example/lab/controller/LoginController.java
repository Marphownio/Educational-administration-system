package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static java.lang.Integer.parseInt;

@Controller
public class LoginController {
    @Resource
    public UserService userService;

    // 登录
    @RequestMapping(value = "/login")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model, HttpSession session) {
        if(!id.matches("^\\d{6}$") && !id.matches("^\\d{8}$")){
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }
        User user = userService.findUserById(parseInt(id));
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }
        else {
            session.setAttribute("user", user);
            // 初次登录，需要重置密码
            if (password.equals("fudan123456")) {
                return "resetPassword";
            }
            // 跳转到首页
            return "redirect:index";
        }
    }
}
