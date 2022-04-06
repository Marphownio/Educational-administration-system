package com.example.lab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public  class LogoutController {

    // 注销
    @RequestMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


