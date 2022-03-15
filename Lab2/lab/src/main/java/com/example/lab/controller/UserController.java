package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    //增加用户
    @PostMapping(value = "")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
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
