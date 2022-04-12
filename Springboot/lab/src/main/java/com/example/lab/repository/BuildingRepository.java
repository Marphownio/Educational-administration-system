package com.example.lab.repository;

import com.example.lab.pojo.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building,Integer> {
}
