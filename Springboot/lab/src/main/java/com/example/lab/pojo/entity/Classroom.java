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
    private Integer classroomId;

    // 该教室所属教学楼
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}

