package com.example.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @RequestMapping("/resetPassword")
    public String resetPassword(@RequestParam("newPassword1") String newPassword1, @RequestParam("newPassword2") String newPassword2) {
        //To Do:重置密码

        return "redirect:/homepage"; //重定向到主页
    }

}
