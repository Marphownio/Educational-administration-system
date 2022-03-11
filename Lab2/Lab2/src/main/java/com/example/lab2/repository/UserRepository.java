package com.example.lab2.repository;

import com.example.lab2.pojo.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
