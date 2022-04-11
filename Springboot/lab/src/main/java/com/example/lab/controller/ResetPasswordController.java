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

        if(newPassword.equals(user.getUserPassword())) {
            return ResultMessage.FAILED;
        }
        else {
            user.setUserPassword(newPassword);
            try {
                userService.updateUser(user);
                ResultMessage resultMessage;
                switch (user.getRole()) {
                    case TEACHER:
                        resultMessage = ResultMessage.SUCCESS_LOGIN_TEACHER; break;
                    case STUDENT:
                        resultMessage = ResultMessage.SUCCESS_LOGIN_STUDENT; break;
                    default:
                        resultMessage = ResultMessage.FAILED; break;
                }
                return resultMessage;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }
}
