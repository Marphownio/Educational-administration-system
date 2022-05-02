package com.example.lab.repository;

import com.example.lab.pojo.entity.TeacherApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<TeacherApplication, Integer> {
}
