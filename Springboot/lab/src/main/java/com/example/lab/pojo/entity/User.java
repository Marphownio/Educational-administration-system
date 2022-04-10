package com.example.lab.pojo.entity;

import com.example.lab.pojo.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("role")
@Proxy(lazy = false)
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

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

}
