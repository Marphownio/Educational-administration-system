package com.example.lab.pojo.entity;

import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Admin {

    @Id
    private Integer userId;

    private String password;
    private UserRole role;

    // 选课系统状态
    private CourseSelectionStatus courseSelectionStatus;

    // 系统学年学期
    private String academicYear;
    private String term;

    public Admin(Integer userId, String password, UserRole userRole, CourseSelectionStatus courseSelectionStatus, String academicYear, String term){
        this.userId = userId;
        this.password = password;
        this.role = userRole;
        this.courseSelectionStatus = courseSelectionStatus;
        this.academicYear = academicYear;
        this.term = term;
    }

    public Admin() {

    }
}
