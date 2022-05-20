package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.ClassArrangement;

import java.util.List;

public interface ClassArrangementService {

    ClassArrangement addClassArrangement(ClassArrangement classArrangement);

    ResultMessage deleteClassArrangement(Integer classArrangementId);

    List<ClassArrangement> findAllClassArrangement();

}
