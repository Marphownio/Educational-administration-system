package com.example.lab.pojo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

// 专业
@Entity
@Data
public class Major {

    @Id
    private Integer majorId;

    private String majorName;
    private String introduction;


    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "schoolId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private School school;

    @OneToMany(targetEntity = Course.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "courseId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Course> courses;

    @OneToMany(targetEntity = User.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<User> students;
}
