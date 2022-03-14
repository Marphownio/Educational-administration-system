package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    private String id; // student number or employee number
    private String password;
    private Integer role; // 0:admin, 1:teacher, 2:student
    private String name;
    private String idNumber; // identity card number
    private String phoneNumber;
    private String email;
}
