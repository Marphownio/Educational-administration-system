package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

// 学生的申请
@Entity
@Getter
@Setter
@Proxy(lazy = false)
public class StudentApplication {

    // 申请id
    @Id
    @Column(name = "application_id")
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String reason;

    public StudentApplication(Course course,Student student,String reason){
        this.setCourse(course);
        this.setStudent(student);
        this.setReason(reason);
    }

    public StudentApplication(){

    }
}
