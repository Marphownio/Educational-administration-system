package com.example.lab2.pojo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

