package com.example.lab.service.impl;

import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.AdminRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

import static java.lang.Math.min;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private CourseService courseService;

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

    @Override
    public ResultMessage changeCourseSelectionStatus() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Admin admin = this.getAdmin();
        try {
            switch (admin.getCourseSelectionStatus()) {
                case START_TERM:    admin.setCourseSelectionStatus(CourseSelectionStatus.START_FIRST);  break;
                case START_FIRST:   admin.setCourseSelectionStatus(CourseSelectionStatus.END_FIRST);    break;
                case END_FIRST:     admin.setCourseSelectionStatus(CourseSelectionStatus.START_SECOND);
                    // 第一轮选课结果筛选
                    resultMessage = firstScreening(); break;
                case START_SECOND:  admin.setCourseSelectionStatus(CourseSelectionStatus.END_SECOND);   break;
                case END_SECOND:    admin.setCourseSelectionStatus(CourseSelectionStatus.END_TERM);     break;
                case END_TERM:      admin.setCourseSelectionStatus(CourseSelectionStatus.START_TERM);   break;
            }
            if (resultMessage == ResultMessage.SUCCESS) {
                resultMessage = saveAdmin(admin);
            }
        }
        catch (Exception e) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage firstScreening() {
        Admin admin = this.getAdmin();
        List<Course> courses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // 备份，失败时回滚
        List<Course> backupCourses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // TODO: 更合理的筛选, 课程时间冲突，模块复选
        try {
            for (Course course : courses) {
                List<Student> students = new ArrayList<>(course.getStudents());
                if (students.isEmpty()) {
                    continue;
                }
                course.getStudents().clear();
                course.setStudents(new HashSet<>(students.subList(0, min(course.getCapacity(), students.size())-1)));
                if (courseService.updateCourse(course) == ResultMessage.FAILED) {
                    for (Course course1 : backupCourses) {
                        courseService.updateCourse(course1);
                    }
                    return ResultMessage.FAILED;
                }
            }
            return ResultMessage.SUCCESS;
        }
        catch (Exception e) {
            for (Course course : backupCourses) {
                courseService.updateCourse(course);
            }
            return ResultMessage.FAILED;
        }
    }
}
