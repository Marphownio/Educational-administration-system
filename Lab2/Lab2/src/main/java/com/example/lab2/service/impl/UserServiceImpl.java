package com.example.lab2.service.impl;

import com.example.lab2.pojo.entity.User;
import com.example.lab2.repository.UserRepository;
import com.example.lab2.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
