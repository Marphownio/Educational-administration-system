package com.example.lab.pojo;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Major{

    @Id
    private Integer majorId;
    private String majorName;
    private String introduction;


    @ManyToOne
    @JoinColumn(name = "schoolId")
    private School school;

    @OneToMany
    @JoinColumn(name = "courseId")
    private List<Course> courses;

    //学生
    @OneToMany
    @JoinColumn(name = "userId")
    private List<User> students;
}
