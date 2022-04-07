package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.example.lab.LabApplication.admin;
import static java.lang.Integer.parseInt;

@RestController
public class LoginController {

    @Resource
    public UserService userService;

    // 登录
    @PostMapping(value = "/login")
    public ResponseEntity<ResultMessage> login(@RequestParam("loginid") String userId, @RequestParam("loginpw") String password, HttpSession session) {


//        if(!userId.matches("^\\d{6}$") && !userId.matches("^\\d{8}$")){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        else

        if(admin.getUserId().equals(parseInt(userId)) && password.equals(admin.getPassword())) {
            return new ResponseEntity<>(ResultMessage.SUCCESS_LOGIN_ADMIN, HttpStatus.OK);
        }
        else {
            User user = userService.findUserByUserId(parseInt(userId));
                if (user != null && user.getPassword().equals(password) && user.getStatus()) {
                    if (user.getRole() == UserRole.TEACHER) {
                        return new ResponseEntity<>(ResultMessage.SUCCESS_LOGIN_TEACHER, HttpStatus.OK);
                    } else if (user.getRole() == UserRole.STUDENT) {
                        return new ResponseEntity<>(ResultMessage.SUCCESS_LOGIN_STUDENT, HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(ResultMessage.FAILED, HttpStatus.FORBIDDEN);
            }
    }
}
