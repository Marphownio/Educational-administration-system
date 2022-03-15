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

    //增加用户
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
    //删除用户
    @Override
    public void deleteUser(Integer  id) {
        userRepository.deleteById(id);
    }
    //修改用户
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    //查询全部用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
    //通过id查询用户
    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
