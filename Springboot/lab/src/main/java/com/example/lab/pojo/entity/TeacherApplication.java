package com.example.lab.pojo.entity;

import com.example.lab.pojo.enums.ApplicationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 教师的申请
@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class TeacherApplication {

    // 申请id
    @Id
    @Column(name = "application_id")
    private Integer applicationId;

    // 课程id
    private Integer courseId;

    // 课程编号
    private Integer courseNumber;

    // 选课容量
    private Integer capacity;

    // 课程介绍
    @Column(length = 1024)
    private String introduction;

    // 该课程所属课程类
    @ManyToOne
    @JoinColumn(name = "course_category_id")
    private CourseCategory courseCategory;

    // 面向开放的专业
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "application_id")
    private Set<Major> openToMajors = new HashSet<>();

    // 课程安排，一个课程一星期可能包含多次课，一节课对应一个安排
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private Set<ClassArrangement> classArrangements = new HashSet<>();

    // 任课教师
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // 申请的处理方式
    @Column(nullable = false)
    private ApplicationType type;
}
