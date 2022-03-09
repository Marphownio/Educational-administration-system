package com.example.lab2.service;

import com.example.lab2.pojo.entity.User;

import java.util.List;

public interface UserService {

    //增加用户
    User addUser(User user);

    //删除用户
    void deleteUser(Integer id);

    //修改用户
    User updateUser(User user);

    //查询全部用户
    List<User> getAllUser();

    //通过id查询用户
    User getUserById(Integer id);
}
