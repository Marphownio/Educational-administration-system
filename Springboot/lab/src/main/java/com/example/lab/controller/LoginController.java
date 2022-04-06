package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<User> login(@RequestParam("loginid") String userId, @RequestParam("loginpw") String password, Model model, HttpSession session) {

        if(!userId.matches("^\\d{6}$") && !userId.matches("^\\d{8}$")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else if(password.equals("fudan_admin") && userId.equals("000000")){
            User admin=new User();
            admin.setRole(0);
            session.setAttribute("admin", admin);
            return ResponseEntity.ok(admin);
        }
        else {
            User user = userService.findUserByUserId(parseInt(userId));
            if (user == null || !user.getPassword().equals(password)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            else {
                session.setAttribute("user", user);
                return ResponseEntity.ok(user);
//                // 初次登录，需要重置密码
//                if(!userId.matches("^\\d{6}$")){
//                    //学生登录
//                    return ResponseEntity.ok(user);
//                    if (password.equals("fudan123456")) {
//                        //重置密码
//                        return ResponseEntity.status(0);
//                    }
//                    else{
//                        // 跳转到首页
//                        return ResponseEntity.ok(user);
//                    }
//                }
//                if(!userId.matches("^\\d{8}$")){
//                    //教师登录
//                    if (password.equals("fudan123456")) {
//                        //重置密码
////                        return new ResponseEntity<>();
//                    }
//                    else{
//                        // 跳转到首页
//                        System.out.println(userId + "   " + password + "    success2");
//                        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
//                    }
//                }






                // 跳转到首页
//                System.out.println(userId + "   " + password + "    success2");
//                return new ResponseEntity<>("Hello World!", HttpStatus.OK);
            }
        }
    }
}
