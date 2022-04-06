package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.*;

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
