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
public class Teacher extends User {

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();

    // 通过用户产生一个教师
    public Teacher(User user) {
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

    public Teacher() {

    }
}
