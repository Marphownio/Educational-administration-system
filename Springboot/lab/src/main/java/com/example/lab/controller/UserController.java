package com.example.lab.controller;

import com.example.lab.pojo.Course;
import com.example.lab.pojo.Major;
import com.example.lab.pojo.School;
import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        School school = new School();
        Major major = new Major();
        List<Course> courseList=new ArrayList<>();
        user.setSchool(school);
        user.setMajor(major);
        user.setCourses(courseList);
        user.setStatus(false);

        model.addAttribute("msg", userService.addUser(user));
        return "userManage";
    }

    // 删除用户
    @DeleteMapping(value = "/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("msg", userService.deleteUser(userId));
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
    @GetMapping(value = "/{userId}")
    @ResponseBody
    public User findUserById(@PathVariable("userId") Integer userId) {
        return userService.findUserByUserId(userId);
    }
}
