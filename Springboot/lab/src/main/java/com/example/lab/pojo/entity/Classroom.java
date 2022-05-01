package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class Classroom {

    // 教室编号
    @Id
    @Column(name = "classroom_id")
    private Integer classroomId;

    // 教室容量
    private Integer capacity;

    // 该教室所属教学楼
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}

