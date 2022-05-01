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
public class Course extends KindCourse {

    // 课程编号
    @Column(name = "course_id", unique = true)
    private Integer courseId;

    //上课时间
    private String courseTime;

    //上课地点
    private String coursePlace;

    // 选课容量
    private String capacity;

    // 课程安排，一个课程一星期可能包含多次课，一节课对应一个安排
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_arrangement_id")
    private Set<ClassArrangement> classArrangements = new HashSet<>();

    // 课程介绍
    @Column(length = 1024)
    private String introduction;

    // 任课教师
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private Teacher teacher;

    // 学生
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Course_Students",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName ="user_id")})
    private Set<Student> students = new HashSet<>();

}
