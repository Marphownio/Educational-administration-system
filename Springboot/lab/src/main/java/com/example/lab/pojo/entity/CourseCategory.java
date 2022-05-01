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
public class CourseCategory {

    @Id
    @Column(name = "course_category_id")
    private Integer courseCategoryId;

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

    // 面向开放的专业
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Major_CourseCategory",
            joinColumns = {@JoinColumn(name = "course_category_id", referencedColumnName = "course_category_id")},
            inverseJoinColumns = {@JoinColumn(name = "major_id", referencedColumnName ="major_id")})
    private Set<Major> openToMajors = new HashSet<>();

    // 所属院系
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // 该类课程下的所有课程
    @JsonIgnore
    @OneToMany(mappedBy = "courseCategory", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Course> courses;
}
