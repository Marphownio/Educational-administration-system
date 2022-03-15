package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    //增加用户
    @RequestMapping(value ="/adduser")
    public String addUser(@RequestParam("id") Integer id, @RequestParam("role") Integer role, @RequestParam("name") String name, @RequestParam("idNumber") String idNumber,@RequestParam("phoneNumber") String phoneNumber,@RequestParam("email") String email) {
        User user=new User();
        user.setId(id);
        user.setPassword("123456");
        user.setName(name);
        user.setRole(role);
        user.setIdNumber(idNumber);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        userService.addUser(user);
        return "userManage";
    }

    //删除用户
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    //修改用户
    @ResponseBody
    @PutMapping(value = "")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    //查询全部用户
    @ResponseBody
    @GetMapping(value = "")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    //通过id查询用户
    @ResponseBody
    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }
}
