package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class Building {

    // 教学楼编号
    @Id
    private Integer buildingId;

    // 教学楼名称
    private String buildingName;

    // 教学楼中的所有教室
    @OneToMany(mappedBy = "building", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<Classroom> classrooms = new HashSet<>();

}
