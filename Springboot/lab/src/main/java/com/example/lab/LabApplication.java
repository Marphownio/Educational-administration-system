package com.example.lab;

import com.example.lab.pojo.Admin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LabApplication {

    public static final Admin admin = new Admin();

    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }

}
