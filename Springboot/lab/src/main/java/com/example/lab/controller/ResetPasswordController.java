package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class ResetPasswordController {

    @Resource
    public UserService userService;

    @RequestMapping(value ="/resetPassword")
    public String resetPassword(@RequestParam("newPassword1") String newPassword, Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(newPassword.equals(user.getPassword())) {
            model.addAttribute("msg", "请输入与原密码不同的新密码");
            return "resetPassword";
        }
        else {
            user.setPassword(newPassword);
            userService.updateUser(user);
            return "redirect:index";
        }
    }
}
