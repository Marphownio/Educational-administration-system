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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // 所属院系
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // 该类课程下的所有课程
    @JsonIgnore
    @OneToMany(mappedBy = "courseCategory", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();
}
