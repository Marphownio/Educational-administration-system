package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Teacher;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherRepository teacherRepository;

    @Override
    public Teacher findTeacherByTeacherId(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherRepository.findAll();
    }
}
