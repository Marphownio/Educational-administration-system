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
    public ResponseEntity<String> login(@RequestParam("loginid") String userId, @RequestParam("loginpw") String password, Model model, HttpSession session) {

        if(!userId.matches("^\\d{6}$") && !userId.matches("^\\d{8}$")){
            model.addAttribute("msg","用户名或密码错误");
            System.out.println(userId + "   " + password + "    error1");   // 测试用，可删除，下同
            return new ResponseEntity<>("Hello World!", HttpStatus.FORBIDDEN);
        }
        else {
            User user = userService.findUserByUserId(parseInt(userId));
            if (user == null || !user.getPassword().equals(password)) {
                model.addAttribute("msg", "用户名或密码错误");
                System.out.println(userId + "   " + password + "    error2");
                return new ResponseEntity<>("Hello World!", HttpStatus.FORBIDDEN);
            }
            else {
                session.setAttribute("user", user);
                // 初次登录，需要重置密码
                if (password.equals("fudan123456")) {
                    System.out.println(userId + "   " + password + "    success1");
                    return new ResponseEntity<>("Hello World!", HttpStatus.OK);
                }
                // 跳转到首页
                System.out.println(userId + "   " + password + "    success2");
                return new ResponseEntity<>("Hello World!", HttpStatus.OK);
            }
        }
    }
}
