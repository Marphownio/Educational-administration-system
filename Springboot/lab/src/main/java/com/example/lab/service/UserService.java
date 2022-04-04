package com.example.lab.service;

import com.example.lab.pojo.User;

import java.util.List;

public interface UserService {

    // 增加用户
    String addUser(User user);

    // 删除用户
    String deleteUser(Integer userId);

    // 修改用户
    String updateUser(User user);

    // 查询全部用户
    List<User> findAllUser();

    // 通过id查询用户
    User findUserByUserId(Integer userId);

    // 通过名称查询用户
    User findUserByUserName(String username);
}
