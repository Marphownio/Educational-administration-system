package com.example.lab.repository;

import com.example.lab.pojo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School,Integer> {
    School findBySchoolName(String facultyName);
}
