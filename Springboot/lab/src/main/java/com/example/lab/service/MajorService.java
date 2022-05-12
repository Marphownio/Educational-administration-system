package com.example.lab.service;

import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.enums.ResultMessage;

import java.util.List;

public interface MajorService {

    // 增加专业
    ResultMessage addMajor(Major major);

    // 删除专业
    ResultMessage deleteMajor(Integer majorId);

    // 更新专业
    ResultMessage updateMajor(Major major);

    // 查询全部专业
    List<Major> findAllMajor();

    // 通过id查询专业
    Major findMajorByMajorId(Integer majorId);

    // 通过名称查询专业
    Major findMajorByMajorName(String majorName);


}
