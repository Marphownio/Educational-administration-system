package com.example.lab.repository;

import com.example.lab.pojo.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom,Integer> {
}
