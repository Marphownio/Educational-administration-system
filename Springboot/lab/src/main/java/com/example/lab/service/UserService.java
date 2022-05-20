package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    // 登录
    ResultMessage login(String userId, String password, HttpSession session);

    ResultMessage loginResult(User user, HttpSession session);

    // 注销
    ResultMessage logout(HttpSession session);

    ResultMessage resetPassword(String newPassword, HttpSession session);

    ResultMessage saveUser(User user);

    // 增加用户
    ResultMessage addUser(User user);

    // 批量导入用户
    HashMap<String,String> batchImportUser(MultipartFile file);

    // 删除用户
    ResultMessage deleteUser(Integer userId);

    // 修改用户
    ResultMessage updateUser(User user);

    // 查询全部用户
    List<User> findAllUser();

    // 通过id查询用户
    User findUserByUserId(Integer userId);

    // 通过名称查询用户
    List<User> findUserByUserName(String username);

}
