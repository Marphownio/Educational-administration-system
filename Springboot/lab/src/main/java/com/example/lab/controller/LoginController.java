package com.example.lab.controller;

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

import static java.lang.Integer.parseInt;

@RestController
public class LoginController {

    @Resource
    public UserService userService;

    // 登录
    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestParam("loginid") String userId, @RequestParam("loginpw") String password, HttpSession session) {

//        if(!userId.matches("^\\d{6}$") && !userId.matches("^\\d{8}$")){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        else
            if(password.equals("fudan_admin") && userId.equals("000000")){
            User admin=new User();
            admin.setUserId(0);
            admin.setPassword("fudan_admin");
            admin.setRole(UserRole.ADMIN);
            session.setAttribute("admin", admin);
            return ResponseEntity.ok(admin);
        }
        else {
            User user = userService.findUserByUserId(parseInt(userId));
            if (user == null || !user.getPassword().equals(password) || !user.getStatus()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            else {
                session.setAttribute("user", user);
                return ResponseEntity.ok(user);
            }
        }
    }
}
