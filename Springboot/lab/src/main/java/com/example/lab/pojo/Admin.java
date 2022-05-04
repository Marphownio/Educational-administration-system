package com.example.lab.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

    private Integer userId;
    private String password;
    private UserRole role;

    private String userDefaultPassword;

    // 选课系统状态
    private CourseSelectionStatus courseSelectionStatus;

    // 系统学年学期
    private String academicYear;
    private String term;

    public Admin(Integer userId, String password, UserRole userRole, String userDefaultPassword, CourseSelectionStatus courseSelectionStatus, String academicYear, String term){
        this.userId = userId;
        this.password = password;
        this.role = userRole;
        this.userDefaultPassword = userDefaultPassword;
        this.courseSelectionStatus = courseSelectionStatus;
        this.academicYear = academicYear;
        this.term = term;
    }

}
