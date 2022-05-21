package com.example.lab.service;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.entity.ClassArrangement;

public interface ClassArrangementService {

    ClassArrangement addClassArrangement(ClassArrangement classArrangement);

    ResultMessage deleteClassArrangement(Integer classArrangementId);

}
