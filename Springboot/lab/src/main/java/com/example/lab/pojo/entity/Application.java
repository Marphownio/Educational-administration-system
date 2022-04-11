package com.example.lab.pojo.entity;

import com.example.lab.pojo.ApplicationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 申请
@Entity
@Getter
@Setter
public class Application {

    // 申请id、课程id
    @Id
    private Integer applicationId;
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
