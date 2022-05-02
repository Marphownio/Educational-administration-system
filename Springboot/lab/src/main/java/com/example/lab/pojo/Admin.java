package com.example.lab.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

    private Integer userId;
    private String password;
    private UserRole role = UserRole.ADMIN;

    // 选课系统状态
    private CourseSelectionStatus courseSelectionStatus;

    // 系统学年学期
    private String academicYear;
    private String term;
}
