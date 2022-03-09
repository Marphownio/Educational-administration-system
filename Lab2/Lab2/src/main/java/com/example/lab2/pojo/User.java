package com.example.lab2.pojo;

import lombok.Data;

@Data
public class User {
    private String password;
    private Integer role; // student or teacher
    private Integer id; // student number or employee number
    private String name;
    private String IDNumber; // identity card number
    private String phoneNumber;
    private String email;
}
