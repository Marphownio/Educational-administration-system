package com.example.lab.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ClassTime {

    // 第几节课，1-13
    @Id
    private Integer classTimeId;

    // 从几点到几点，如果有更好的类型，可以更改
    private String time;
}
