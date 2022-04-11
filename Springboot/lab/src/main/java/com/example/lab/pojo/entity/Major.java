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

    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
}
