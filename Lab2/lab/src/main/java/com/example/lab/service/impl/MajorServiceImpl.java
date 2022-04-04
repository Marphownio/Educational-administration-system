package com.example.lab.service.impl;

import com.example.lab.pojo.Major;
import com.example.lab.repository.MajorRepository;
import com.example.lab.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Resource
    private MajorRepository majorRepository;

    @Override
    public String addMajor(Major major) {
        String resultMsg;
        if (findMajorByMajorId(major.getMajorId()) != null) {
            resultMsg = "该专业存在，添加失败！";
        }
        else {
            try {
                majorRepository.save(major);
                resultMsg = "添加成功！";
            }
            catch (Exception e) {
                resultMsg = "添加失败！";
            }
        }
        return resultMsg;
    }

    @Override
    public String deleteMajor(Integer majorId) {
        String resultMsg;
        if (findMajorByMajorId(majorId) == null) {
            resultMsg = "该专业不存在，删除失败！";
        }
        else {
            try {
                majorRepository.deleteById(majorId);
                resultMsg = "删除成功！";
            }
            catch (Exception e) {
                resultMsg = "删除失败！";
            }
        }
        return resultMsg;
    }

    @Override
    public String updateMajor(Major major) {
        String resultMsg;
        if (findMajorByMajorId(major.getMajorId()) != null) {
            resultMsg = "专业不存在！";
        }
        else {
            try {
                majorRepository.save(major);
                resultMsg = "修改成功！";
            }
            catch (Exception e) {
                resultMsg = "修改失败！";
            }
        }
        return resultMsg;
    }

    @Override
    public Major findMajorByMajorId(Integer id) {
        return majorRepository.findById(id).orElse(null);
    }

    @Override
    public Major findMajorByMajorName(String majorName) {
        return majorRepository.findByMajorName(majorName);
    }


    @Override
    public List<Major> findAllMajor() {
        return majorRepository.findAll();
    }
}
