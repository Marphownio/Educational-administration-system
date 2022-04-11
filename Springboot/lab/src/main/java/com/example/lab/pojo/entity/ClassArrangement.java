package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Getter
@Setter
// 一节课的课程安排，包括时间和地点
public class ClassArrangement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer classArrangementId;

    @OneToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private DayOfWeek dayOfWeek;

    @OneToOne
    @JoinColumn(name = "class_time_id")
    private ClassTime classTime;
}
