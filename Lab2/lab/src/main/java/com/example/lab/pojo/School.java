package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

//学院类
@Entity
@Data
public class School {

    @Id
    private Integer schoolId;
    private String schoolName;
    private String introduce;


    @OneToMany
    @JoinColumn(name = "majorId")
    private List<Major> majors;

    @OneToMany
    @JoinColumn(name = "courseId")
    private List<Course> courses;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<User> users;
}
