package com.example.lab.pojo.entity;

import com.example.lab.pojo.ApplicationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 申请
@Entity
@Getter
@Setter
public class Application {

    // 申请id
    @Id
    private Integer applicationId;

    // 课程id
    private Integer courseId;

    // 课程名
    private String courseName;
    // 学时
    private Integer classHour;
    // 学分
    private Integer credit;

    // 课程安排，一个课程一星期可能包含多次课，一节课对应一个安排
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<ClassArrangement> classArrangements = new HashSet<>();

    //上课时间
    private String courseTime;

    //上课地点
    private String coursePlace;

    // 选课容量
    private String capacity;

    // 课程介绍
    private String introduction;

    // 所属专业
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // 开课院系
    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    // 任课教师
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // 申请的处理方式
    @Column(nullable = false)
    private ApplicationType type;
}
