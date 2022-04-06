package com.example.lab.controller;

import com.example.lab.pojo.Course;
import com.example.lab.pojo.Major;
import com.example.lab.pojo.School;
import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    // 获取用户信息
    @GetMapping(value = "/userInfo")
    public User getUserInfo(HttpSession httpSession) {
        return  (User)httpSession.getAttribute("user");
    }

    // 增加用户
    @PostMapping(value = "")
    public ResponseEntity<String> addUser(User user) {

        user.setPassword("fudan123456"); // 统一设置初始密码
//        School school = new School();
//        Major major = new Major();
        List<Course> courseList=new ArrayList<>();
//        user.setSchool(school);
//        user.setMajor(major);
        user.setCourses(courseList);
        user.setStatus(true);

        //

        switch (userService.addUser(user)){
            case "学号/工号已存在，添加失败！":
                return new ResponseEntity<>("学号/工号已存在，添加失败！",HttpStatus.NOT_IMPLEMENTED);
            case "添加成功！":
                return new ResponseEntity<>("添加成功",HttpStatus.OK);
            case "添加失败！":
                return new ResponseEntity<>("添加失败",HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);

    }

    // 删除用户
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {

        switch (userService.deleteUser(userId)){
            case "用户不存在":
                return new ResponseEntity<>("用户不存在",HttpStatus.NOT_IMPLEMENTED);
            case "删除成功":
                return new ResponseEntity<>("删除成功！",HttpStatus.OK);
            case "删除失败":
                return new ResponseEntity<>("删除失败！",HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    // 修改用户
    @PutMapping(value = "")
    public ResponseEntity<String> updateUser(User user) {
        switch ( userService.updateUser(user)){
            case "用户不存在":
                return new ResponseEntity<>("用户不存在",HttpStatus.NOT_IMPLEMENTED);
            case "修改成功":
                return new ResponseEntity<>("修改成功！",HttpStatus.OK);
            case "修改失败":
                return new ResponseEntity<>("修改失败！",HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);

    }

    // 查询全部用户
    @GetMapping(value = "")
    public ResponseEntity<List<User>> findAllUser() {

        List<User> userList = new ArrayList<>(userService.findAllUser()) ;
        if (userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);

    }

    // 通过id查询用户
    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable("userId") Integer userId) {

        User user = userService.findUserByUserId(userId);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("username") String username) {

        User user = userService.findUserByUserName(username);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(userService.findUserByUserName(username), HttpStatus.OK);

    }

}
