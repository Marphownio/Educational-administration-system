package com.example.lab.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Proxy(lazy = false)
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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name="arrangement_time",joinColumns={@JoinColumn(name="class_arrangement_id")},
            inverseJoinColumns ={@JoinColumn(name="class_time_id")})
    private Set<ClassTime> classTimes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private TeacherApplication application;
}
