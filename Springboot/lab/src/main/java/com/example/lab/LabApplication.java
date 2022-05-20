package com.example.lab;

import com.example.lab.pojo.entity.Admin;
import com.example.lab.pojo.entity.ClassTime;
import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.UserRole;
import com.example.lab.service.AdminService;
import com.example.lab.service.ClassTimeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Component
public class LabApplication {

    @Resource
    private AdminService adminService;

    @Resource
    private ClassTimeService classTimeService;

    private static AdminService adminService2;
    private static ClassTimeService classTimeService2;

    @PostConstruct
    private void initService() {
        adminService2 = adminService;
        classTimeService2 = classTimeService;
    }
    public static void prepare() {
        Admin admin = new Admin(0,"fudan_admin", UserRole.ADMIN, CourseSelectionStatus.START_TERM,"2021-2022","1");
        if (adminService2.getAdmin() == null) {
            adminService2.saveAdmin(admin);
        }
        if (classTimeService2.findAllClassTime().isEmpty()) {
            Set<ClassTime> classTimes = new HashSet<>();
            classTimes.add(new ClassTime(1, 8, 0, 8, 45));
            classTimes.add(new ClassTime(2, 8, 55, 9, 40));
            classTimes.add(new ClassTime(3, 9, 55, 10, 40));
            classTimes.add(new ClassTime(4, 10, 50, 11, 35));
            classTimes.add(new ClassTime(5, 11, 45, 12, 30));
            classTimes.add(new ClassTime(6, 13, 30, 14, 15));
            classTimes.add(new ClassTime(7, 14, 25, 15, 10));
            classTimes.add(new ClassTime(8, 15, 25, 16, 10));
            classTimes.add(new ClassTime(9, 16, 20, 17, 5));
            classTimes.add(new ClassTime(10, 17, 15, 18, 0));
            classTimes.add(new ClassTime(11, 18, 30, 19, 15));
            classTimes.add(new ClassTime(12, 19, 25, 20, 10));
            classTimes.add(new ClassTime(13, 20, 20, 21, 5));
            for (ClassTime classTime : classTimes) {
                classTimeService2.addClassTime(classTime);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
        prepare();
    }
}
