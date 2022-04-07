package com.example.lab.pojo.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

// 专业
@Entity
@Data
public class Major {

    @Id
    private Integer majorId;
    private String majorName;
    private String introduction;


    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "schoolId")
    private School school;

    @OneToMany(targetEntity = Course.class)
    @JoinColumn(name = "courseId")
    private List<Course> courses;

    //学生
    @OneToMany(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private List<User> students;
}
