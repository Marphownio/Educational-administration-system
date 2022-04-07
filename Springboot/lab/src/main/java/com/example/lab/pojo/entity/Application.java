package com.example.lab.pojo.entity;

import com.example.lab.pojo.ApplicationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

// 申请
@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationId;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "courseId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Course course;

    private ApplicationType type;
}
