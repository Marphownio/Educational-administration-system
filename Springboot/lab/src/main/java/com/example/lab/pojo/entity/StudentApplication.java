package com.example.lab.pojo.entity;

import com.example.lab.pojo.enums.ApplicationStatus;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String reason;

    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    public StudentApplication(Course course,Student student,String reason){
        this.setCourse(course);
        this.setStudent(student);
        this.setReason(reason);
        this.setApplicationStatus(ApplicationStatus.IN_REVIEW);
    }

    public StudentApplication(){

    }
}
