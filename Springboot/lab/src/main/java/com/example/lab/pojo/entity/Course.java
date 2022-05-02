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
public class Course {

    // 课程id
    @Id
    @Column(name = "course_id")
    private Integer courseId;

    // 课程编号
    private Integer courseNumber;

    // 学年学期
    private String academicYear;
    private String term;

    // 选课容量
    private Integer capacity;

    // 课程介绍
    @Column(length = 1024)
    private String introduction;

    // 该课程所属课程类
    @ManyToOne
    @JoinColumn(name = "course_category_id")
    private CourseCategory courseCategory;

    // 课程安排，一个课程一星期可能包含多次课，一节课对应一个安排
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_arrangement_id")
    private Set<ClassArrangement> classArrangements = new HashSet<>();

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
