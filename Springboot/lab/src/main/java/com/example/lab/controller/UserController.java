package com.example.lab.controller;

import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 获取用户信息
    @GetMapping(value = "/info")
    public User getUserInfo(HttpSession httpSession) {
        return  (User)httpSession.getAttribute("user");
    }


    // 增加用户
    @PostMapping(value = "/add")
    public ResponseEntity<String> addUser(User user) {

        switch (userService.addUser(user)) {
            case EXIST:
                return new ResponseEntity<>("学号/工号已存在，添加失败！",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("添加成功",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("添加失败",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误", HttpStatus.NOT_IMPLEMENTED);

        }
    }

    // 删除用户
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
        switch (userService.deleteUser(userId)){
            case NOTFOUND:
                return new ResponseEntity<>("用户不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("删除成功！",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("删除失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误", HttpStatus.NOT_IMPLEMENTED);
        }
    }

    // 修改用户
    @PutMapping(value = "/update")
    public ResponseEntity<String> updateUser(User user) {
        switch ( userService.updateUser(user) ){
            case NOTFOUND:
                return new ResponseEntity<>("用户不存在",HttpStatus.NOT_IMPLEMENTED);
            case SUCCESS:
                return new ResponseEntity<>("修改成功！",HttpStatus.OK);
            case FAILED:
                return new ResponseEntity<>("修改失败！",HttpStatus.NOT_IMPLEMENTED);
            default:
                return new ResponseEntity<>("未知错误", HttpStatus.NOT_IMPLEMENTED);
        }
    }

    // 查询全部用户
    @GetMapping(value = "/list")
    public ResponseEntity<Set<User>> findAllUser() {

        Set<User> users = new HashSet<>(userService.findAllUser());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    // 通过id查询用户
    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable("userId") Integer userId) {

        User user = userService.findUserByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 通过姓名查询用户
    @GetMapping(value = "/{username}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("username") String username) {

        User user = userService.findUserByUserName(username);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(userService.findUserByUserName(username), HttpStatus.OK);
    }
}
