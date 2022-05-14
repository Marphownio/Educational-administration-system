package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.AdminRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepository adminRepository;

    @Override
    public ResultMessage saveAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
        }
        catch (Exception e) {
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public Admin getAdmin() {
        return adminRepository.findById(0).orElse(null);
    }

    @Override
    public ResultMessage setAcademicYearAndTerm(String academicYear, String term) {
        Admin admin = this.getAdmin();
        try {
            admin.setAcademicYear(academicYear);
            admin.setTerm(term);
            this.saveAdmin(admin);
            return ResultMessage.SUCCESS;
        }
        catch (Exception e) {
            return ResultMessage.FAILED;
        }
    }
}
