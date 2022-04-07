package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

//学院类
@Entity
@Data
public class School {

    @Id
    private Integer schoolId;

    private String schoolName;
    private String introduction;


    @OneToMany(targetEntity = Major.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "majorId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Major> majors;

    @OneToMany(targetEntity = Course.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Course> courses;

    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<User> users;
}
