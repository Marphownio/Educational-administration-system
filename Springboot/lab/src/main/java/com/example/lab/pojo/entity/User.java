package com.example.lab.pojo.entity;

import com.example.lab.pojo.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("role")
public class User {

    @Id
    @Column(name = "user_id",length = 8)
    private Integer userId;

    @Column(length = 32)
    private String password = "fudan123456";

    @Column(nullable = false)
    private UserRole role;

    private String username;

    @Column(length = 18)
    private String idNumber;

    @Column(length = 11)
    private String phoneNumber;

    private String email;
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolId")
    private School school;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "majorId")
    private Major major;

}
