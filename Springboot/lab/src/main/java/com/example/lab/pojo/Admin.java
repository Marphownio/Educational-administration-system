package com.example.lab.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

    private Integer userId = 0;
    private String password;
    private UserRole role = UserRole.ADMIN;

    // 选课系统开关
    private Boolean courseSelectionSystem = true;

}
