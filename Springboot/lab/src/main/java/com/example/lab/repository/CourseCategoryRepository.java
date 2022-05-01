package com.example.lab.repository;

import com.example.lab.pojo.entity.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory,Integer> {
    CourseCategory findByCourseName(String courseName);
}
