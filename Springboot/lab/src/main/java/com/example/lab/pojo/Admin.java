package com.example.lab.pojo;

import lombok.Data;

@Data
public class Admin {

    private Integer userId = 0;
    private String password = "fudan_admin";
    private Boolean courseSelectionSystem = true;

}
