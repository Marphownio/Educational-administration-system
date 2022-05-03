package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 专业
@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class Major {

    @Id
    @Column(name = "major_id")
    private Integer majorId;

    @Column(unique = true)
    private String majorName;

    @Column(length = 1024)
    private String introduction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // 该专业下的所有种类的课程
    @OneToMany(mappedBy = "major", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<CourseCategory> courseCategories = new HashSet<>();

    // 该专业可选的所有课程
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Major_Course",
            joinColumns = {@JoinColumn(name = "major_id", referencedColumnName = "major_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName ="course_id")})
    private Set<Course> selectableCourses = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
}
