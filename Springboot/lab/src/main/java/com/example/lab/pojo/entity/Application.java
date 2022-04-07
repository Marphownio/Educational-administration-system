package com.example.lab.pojo.entity;

import com.example.lab.pojo.ApplicationType;
import lombok.Data;

import javax.persistence.*;

// 申请
@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationId;

    @OneToOne(targetEntity = Course.class)
    @JoinColumn(name = "courseId")
    private Course course;

    private ApplicationType type;
}
