package com.example.lab.service;

import com.example.lab.pojo.Major;

import java.util.List;

public interface MajorService {

    String addMajor(Major major);

    String deleteMajor(Integer majorId);

    String updateMajor(Major major);

    Major findMajorByMajorId(Integer majorId);

    Major findMajorByMajorName(String majorName);
    List<Major> findAllMajor();
}
