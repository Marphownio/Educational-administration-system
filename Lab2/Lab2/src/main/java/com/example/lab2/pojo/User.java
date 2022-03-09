package com.example.lab2.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id; // student number or employee number
    private String password;
    private Integer role; // student or teacher
    private String name;
    private String IDNumber; // identity card number
    private String phoneNumber;
    private String email;
}

