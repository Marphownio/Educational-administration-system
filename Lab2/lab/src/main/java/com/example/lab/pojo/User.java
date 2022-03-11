package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // student number or employee number
    private String password;
    private Integer role; // student or teacher
    private String name;
    private String idNumber; // identity card number
    private String phoneNumber;
    private String email;

}

