package com.example.lab.pojo.entity;

import com.example.lab.pojo.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    private Integer userId;

    private String password = "fudan123456";
    private UserRole role;
    private String username;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private Boolean status = true;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolId")
    private School school;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorId")
    private Major major;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST)//, fetch = FetchType.EAGER)
    @JsonIgnore
//    @JoinTable(name = "Course_User",
//            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
//            inverseJoinColumns = {@JoinColumn(name = "courseId", referencedColumnName = "courseId")})
    private Set<Course> courses = new HashSet<>();

}
