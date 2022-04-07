package com.example.lab.pojo.entity;

import com.example.lab.pojo.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    private Integer userId;
    private String password = "fudan123456";
    private UserRole role;
    private String username;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private Boolean status;

    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "schoolId")
    private School school;

    @ManyToOne(targetEntity = Major.class)
    @JoinColumn(name = "majorId")
    private Major major;

    @ManyToMany(targetEntity = Course.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "Course_User",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "courseId", referencedColumnName ="courseId")})
    private List<Course> courses;

}
