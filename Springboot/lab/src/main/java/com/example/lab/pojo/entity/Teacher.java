package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Teacher extends User {

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Teacher(User user) {
        this.setUserId(user.getUserId());
        this.setPassword(user.getPassword());
        this.setRole(user.getRole());
        this.setUsername(user.getUsername());
        this.setIdNumber(user.getIdNumber());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setStatus(user.getStatus());

        System.out.println("11");
        this.setSchool(new School());
        this.setMajor(new Major());
        System.out.println("12");
        this.getSchool().setSchoolId(user.getSchool().getSchoolId());
        this.getMajor().setMajorId(user.getMajor().getMajorId());
    }

    public Teacher() {

    }
}
