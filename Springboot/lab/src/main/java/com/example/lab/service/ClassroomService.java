package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.Classroom;

import java.util.List;

public interface ClassroomService {

    ResultMessage addClassroom(Classroom classroom);

    ResultMessage deleteClassroom(Integer classroomId);

    ResultMessage updateClassroom(Classroom classroom);

    Classroom findClassroomById(Integer classroomId);

    List<Classroom> findAllClassroom();
}
