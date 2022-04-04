package com.example.lab.repository;

import com.example.lab.pojo.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findByCourseName(String name);
}