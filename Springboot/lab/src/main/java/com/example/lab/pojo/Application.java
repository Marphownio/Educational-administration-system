package com.example.lab.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Application {
    @Id
    private Integer applicationId;
    @OneToOne
    private Course course;
    private String type;
}
