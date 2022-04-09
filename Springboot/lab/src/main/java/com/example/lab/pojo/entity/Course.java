package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 课程类
@Entity
@Getter
@Setter
public class Course {

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
    @Column(length = 1024)
    private String introduction;

    // 所属专业
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorId", nullable = false)
    private Major major;

    // 开课院系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolId", nullable = false)
    private School school;

    // 任课教师
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    // 学生
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Course_Students",
            joinColumns = {@JoinColumn(name = "courseId", referencedColumnName = "courseId")},
            inverseJoinColumns = {@JoinColumn(name = "studentId", referencedColumnName ="user_id")})
    private Set<Student> students = new HashSet<>();

//    public Course(Application application) {
//
//    }

}
