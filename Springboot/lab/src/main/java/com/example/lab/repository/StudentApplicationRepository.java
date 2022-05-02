package com.example.lab.repository;

import com.example.lab.pojo.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentApplicationRepository extends JpaRepository<StudentApplication,Integer> {
}
