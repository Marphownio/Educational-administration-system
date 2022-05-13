package com.example.lab.service;

import com.example.lab.pojo.entity.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher findTeacherByTeacherId(Integer teacherId);

    List<Teacher> findAllTeacher();

}
