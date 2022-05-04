package com.example.lab;

import com.example.lab.pojo.Admin;
import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabApplication {

    public static final Admin admin = new Admin(
            0,
            "fudan_admin",
            UserRole.ADMIN,
            "fudan123456",
            CourseSelectionStatus.START_TERM,
            "2021-2022",
            "1"
    );

    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }

}
