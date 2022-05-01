package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

// 课程类
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Proxy(lazy = false)
public class KindCourse {

    @Id
    private Integer id;

    // 课程名
    private String courseName;
    // 学时
    private Integer classHour;
    // 学分
    private Integer credit;

    // 所属专业
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    // 开课院系
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
