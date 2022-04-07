package com.example.lab.pojo.entity;

import com.example.lab.pojo.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

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
    private Boolean status = true;

    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "schoolId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private School school;

    @ManyToOne(targetEntity = Major.class)
    @JoinColumn(name = "majorId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Major major;

    @ManyToMany(targetEntity = Course.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "Course_User",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "courseId", referencedColumnName ="courseId")})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Course> courses;

}
