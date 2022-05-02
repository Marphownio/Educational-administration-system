package com.example.lab.service;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.ClassTime;

import java.util.List;

public interface ClassTimeService {

    ResultMessage addClassTime(ClassTime classTime);

    ResultMessage deleteClassTime(Integer classTimeId);

    ResultMessage updateClassTime(ClassTime classTime);

    List<ClassTime> findAllClassTime();

    ClassTime findClassTimeById(Integer classTimeId);
}
