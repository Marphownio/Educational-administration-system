package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//学院类
@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class School {

    @Id
    @Column(name = "school_id")
    private Integer schoolId;

    @Column(unique = true)
    private String schoolName;

    @Column(length = 1024)
    private String introduction;

    @OneToMany(mappedBy = "school", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Major> majors = new HashSet<>();

    @OneToMany(mappedBy = "school", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CourseCategory> courseCategories = new HashSet<>();

    @OneToMany(mappedBy = "school", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "school", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "school", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
}
