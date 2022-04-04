package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    private Integer userId; // 学号/工号
    private String password = "fudan123456";
    private Integer role; // 0:admin, 1:teacher, 2:student
    private String username;
    private String idNumber; // 身份证号
    private String phoneNumber;
    private String email;
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = "schoolId")
    private School school;

    @ManyToOne
    @JoinColumn(name = "majorId")
    private Major major;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "Course_User",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "courseId", referencedColumnName ="courseId")})
    private List<Course> courses;

}
