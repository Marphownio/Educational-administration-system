package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    //增加用户
    @RequestMapping(value ="/adduser")
    public String addUser(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("idNumber") String idNumber,@RequestParam("phoneNumber") String phoneNumber,@RequestParam("email") String email,Model model, HttpSession session) {
        User user=new User();
        user.setName(name);
        user.setId(id);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword("123456");
        userService.addUser(user);
        return "/index";
    }

    //删除用户
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }

    //修改用户
    @PutMapping(value = "")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    //查询全部用户
    @GetMapping(value = "")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    //通过id查询用户
    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable("id") String id) {
        return userService.findUserById(id);
    }
}
