package com.example.lab.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

// 课程类
@Entity
@Data
public class Course {

    // 课程代码
    @Id
    private Integer courseId;
    // 课程名
    private String courseName;
    // 学时
    private Integer classHour;
    // 学分
    private Integer credit;
    // 上课时间
    private String classTime;
    // 上课地点
    private String classPlace;
    // 选课容量
    private String capacity;
    // 课程介绍
    private String introduction;

    // 所属专业
    @ManyToOne(targetEntity = Major.class)
    @JoinColumn(name = "MajorId")
    private Major major;

    // 开课院系
    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "schoolId")
    private School school;

    // 任课教师和学生
    @ManyToMany(targetEntity = User.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "Course_User",
            joinColumns = {@JoinColumn(name = "courseId", referencedColumnName = "courseId")},
            inverseJoinColumns = {@JoinColumn(name = "userId", referencedColumnName ="userId")})
    private List<User> users;

}
