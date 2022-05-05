package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
// 一节课的课程安排，包括时间和地点
public class ClassArrangement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "class_arrangement_id")
    private Integer classArrangementId;

    @OneToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private DayOfWeek dayOfWeek;

    @OneToMany
    @JoinColumn(name = "class_arrangement_id")
    private Set<ClassTime> classTimes = new HashSet<>();

}
