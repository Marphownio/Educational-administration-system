package com.example.lab.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

    private Integer userId;
    private String password;
    private UserRole role;

    // 选课系统状态
    private CourseSelectionStatus courseSelectionStatus;

    // 系统学年学期
    private String academicYear;
    private String term;

    public Admin(){
        this.role = UserRole.ADMIN;
        this.courseSelectionStatus = CourseSelectionStatus.START_TERM;
        this.userId = 0;
        this.password = "fudan_admin";
        this.academicYear = "2021-2022";
        this.term = "春";
    }

}
