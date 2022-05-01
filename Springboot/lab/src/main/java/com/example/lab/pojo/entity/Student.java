package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Proxy(lazy = false)
public class Student extends User {

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "Course_Students",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName ="course_id")})
    private Set<Course> courses = new HashSet<>();

    // 通过用户产生一个学生
    public Student(User user) {
        this.setUserId(user.getUserId());
        this.setPassword(user.getPassword());
        this.setRole(user.getRole());
        this.setUsername(user.getUsername());
        this.setIdNumber(user.getIdNumber());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setEmail(user.getEmail());
        this.setStatus(user.getStatus());

        this.setSchool(new School());
        this.setMajor(new Major());
        this.getSchool().setSchoolId(user.getSchool().getSchoolId());
        this.getMajor().setMajorId(user.getMajor().getMajorId());
    }

    public Student() {

    }
}
