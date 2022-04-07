package com.example.lab.pojo.entity;

import com.example.lab.pojo.ApplicationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 申请
@Entity
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId")
    private Course course = new Course();

    private ApplicationType type;
}
