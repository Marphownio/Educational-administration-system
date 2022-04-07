package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public  class LogoutController {

    // 注销
    @RequestMapping(value = "/logout")
    public ResultMessage logout(HttpSession session) {
        try {
            session.invalidate();
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception) {
            return ResultMessage.FAILED;
        }
    }
}


