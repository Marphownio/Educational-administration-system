package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//学院类
@Entity
@Getter
@Setter
public class School {

    @Id
    private Integer schoolId;

    private String schoolName;
    private String introduction;

    @OneToMany(mappedBy = "majorId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Major> majors = new HashSet<>();

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
