package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    private Integer id; // 学号/工号
    private String password;
    private Integer role; // 0:admin, 1:teacher, 2:student
    private String name;
    private String idNumber; // 身份证号
    private String phoneNumber;
    private String email;
}
