package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ClassTime {

    // 第几节课，1-13
    @Id
    @Column(name = "class_time_id")
    private Integer classTimeId;

    private Integer startTimeHour;
    private Integer startTimeMin;
    private Integer endTimeHour;
    private Integer endTimeMin;
}
