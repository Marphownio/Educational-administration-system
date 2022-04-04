package com.example.lab.repository;

import com.example.lab.pojo.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major,Integer> {
    Major findByMajorName(String majorName);
}
