package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    // 增加用户
    ResultMessage addUser(User user);

    // 批量导入用户
//    boolean BatchImportUser(String name, MultipartFile file);

    // 删除用户
    ResultMessage deleteUser(Integer userId);

    // 修改用户
    ResultMessage updateUser(User user);

    // 查询全部用户
    List<User> findAllUser();

    // 通过id查询用户
    User findUserByUserId(Integer userId);

    // 通过名称查询用户
    User findUserByUserName(String username);
}
