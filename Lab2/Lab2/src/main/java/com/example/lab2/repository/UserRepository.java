package com.example.lab2.repository;

import com.example.lab2.pojo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}