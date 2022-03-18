package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 增加用户
    @PostMapping(value = "")
    public String addUser(User user, Model model) {
        user.setPassword("fudan123456"); // 统一设置初始密码
        model.addAttribute("msg", userService.addUser(user));
        return "userManage";
    }

    // 删除用户
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("msg", userService.deleteUser(id));
        return "userManage";
    }

    // 修改用户
    @PutMapping(value = "")
    public String updateUser(User user, Model model) {
        model.addAttribute("msg", userService.updateUser(user));
        return "userManage";
    }

    // 查询全部用户
    @GetMapping(value = "")
    @ResponseBody
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    // 通过id查询用户
    @GetMapping(value = "/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }
}
