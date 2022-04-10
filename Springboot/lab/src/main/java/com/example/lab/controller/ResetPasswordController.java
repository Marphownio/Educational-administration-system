package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class ResetPasswordController {

    @Resource
    public UserService userService;

    // 重置密码
    @RequestMapping(value ="/resetPassword")
    public ResultMessage resetPassword(@RequestParam("newpw1") String newPassword, HttpSession session) {

        User user = userService.findUserByUserId(((User)session.getAttribute("user")).getUserId());

        if(newPassword.equals(user.getPassword())) {
            return ResultMessage.FAILED;
        }
        else {
            user.setPassword(newPassword);
            try {
                userService.updateUser(user);
                switch (user.getRole()) {
                    case TEACHER:
                        return ResultMessage.SUCCESS_LOGIN_TEACHER;
                    case STUDENT:
                        return ResultMessage.SUCCESS_LOGIN_STUDENT;
                    default:
                        return ResultMessage.FAILED;
                }
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }
}
