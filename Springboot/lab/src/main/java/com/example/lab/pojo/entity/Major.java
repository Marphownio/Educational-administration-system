package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 专业
@Entity
@Getter
@Setter
public class Major {

    @Id
    private Integer majorId;

    private String majorName;
    private String introduction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolId")
    private School school;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
