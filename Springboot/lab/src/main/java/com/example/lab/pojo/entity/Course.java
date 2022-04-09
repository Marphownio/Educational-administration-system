package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 课程类
@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class Course {

    @Id
    @Column(name = "course_id")
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
    @Column(length = 1024)
    private String introduction;

    // 所属专业
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "major_id")
    private Major major;

    // 开课院系
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id")
    private School school;

    // 任课教师
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private Teacher teacher;

    // 学生
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})//, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Course_Students",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName ="user_id")})
    private Set<Student> students = new HashSet<>();

}
