package com.example.lab.repository;

import com.example.lab.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByUsername(String username);
}
