package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.User;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public ResultMessage addUser(User user) {
        if (findUserByUserId(user.getUserId()) != null) {
            return ResultMessage.EXIST;
        }
        else {
            try {
                userRepository.save(user);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage deleteUser(Integer userId) {
        if (findUserByUserId(userId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                userRepository.deleteById(userId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateUser(User user) {
        if (findUserByUserId(user.getUserId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                userRepository.save(user);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
