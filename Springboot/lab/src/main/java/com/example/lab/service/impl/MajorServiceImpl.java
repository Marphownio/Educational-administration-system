package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Major;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.MajorRepository;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

// 专业的增删改查服务
@Service
public class MajorServiceImpl implements MajorService {

    @Resource
    private MajorRepository majorRepository;

    @Resource
    private SchoolService schoolService;

    @Override
    public ResultMessage addMajor(Major major) {
        ResultMessage resultMessage;
        if (findMajorByMajorId(major.getMajorId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if(major.getSchool() == null || schoolService.findSchoolBySchoolId(major.getSchool().getSchoolId()) == null) {
            resultMessage = ResultMessage.FAILED;
        }
        else {
            try {
                majorRepository.save(major);
                resultMessage = ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage deleteMajor(Integer majorId) {
        if (findMajorByMajorId(majorId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                majorRepository.deleteById(majorId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateMajor(Major major) {
        if (findMajorByMajorId(major.getMajorId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                majorRepository.save(major);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
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
