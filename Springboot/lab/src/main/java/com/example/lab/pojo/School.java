package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.List;

//学院类
@Entity
@Data
public class School {

    @Id
    private Integer schoolId;
    private String schoolName;
    private String introduce;


    @OneToMany(targetEntity = Major.class)
    @JoinColumn(name = "majorId")
    private List<Major> majors;

    @OneToMany(targetEntity = Course.class)
    @JoinColumn(name = "courseId")
    private List<Course> courses;

    @OneToMany(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private List<User> users;
}
