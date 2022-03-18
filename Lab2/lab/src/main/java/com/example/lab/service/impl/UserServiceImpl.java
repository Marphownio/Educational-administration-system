package com.example.lab.service.impl;

import com.example.lab.pojo.User;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    // 增加用户
    @Override
    public String addUser(User user) {
        String resultMsg;
        if (findUserById(user.getId()) != null) {
            resultMsg = "学号/工号已存在，添加失败！";
        }
        else {
            try {
                userRepository.save(user);
                resultMsg = "添加成功！";
            }
            catch (Exception e) {
                resultMsg = "身份证号码已存在，添加失败！";
            }
        }
        return resultMsg;
    }
    // 删除用户
    @Override
    public String deleteUser(Integer id) {
        String resultMsg;
        if (findUserById(id) == null) {
            resultMsg = "用户不存在";
        }
        else {
            try {
                userRepository.deleteById(id);
                resultMsg = "删除成功";
            }
            catch (Exception e) {
                resultMsg = "删除失败";
            }
        }
        return resultMsg;
    }
    // 修改用户
    @Override
    public String updateUser(User user) {
        String resultMsg;
        if (findUserById(user.getId()) == null) {
            resultMsg = "用户不存在";
        }
        else {
            try {
                userRepository.save(user);
                resultMsg = "修改成功";
            }
            catch (Exception e) {
                resultMsg = "修改失败";
            }
        }
        return resultMsg;
    }
    // 查询全部用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
    // 通过id查询用户
    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
