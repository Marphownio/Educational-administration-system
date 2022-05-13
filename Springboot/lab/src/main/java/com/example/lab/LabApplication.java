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
            ClassTime classTime1 = new ClassTime(1, 8, 0, 8, 45);
            ClassTime classTime2 = new ClassTime(2, 8, 55, 9, 40);
            ClassTime classTime3 = new ClassTime(3, 9, 55, 10, 40);
            ClassTime classTime4 = new ClassTime(4, 10, 50, 11, 35);
            ClassTime classTime5 = new ClassTime(5, 11, 45, 12, 30);
            ClassTime classTime6 = new ClassTime(6, 13, 30, 14, 15);
            ClassTime classTime7 = new ClassTime(7, 14, 25, 15, 10);
            ClassTime classTime8 = new ClassTime(8, 15, 25, 16, 10);
            ClassTime classTime9 = new ClassTime(9, 16, 20, 17, 5);
            ClassTime classTime10 = new ClassTime(10, 17, 15, 18, 0);
            ClassTime classTime11 = new ClassTime(11, 18, 30, 19, 15);
            ClassTime classTime12 = new ClassTime(12, 19, 25, 20, 10);
            ClassTime classTime13 = new ClassTime(13, 20, 20, 21, 5);
            Set<ClassTime> classTimes = new HashSet<>();
            classTimes.add(classTime1);
            classTimes.add(classTime2);
            classTimes.add(classTime3);
            classTimes.add(classTime4);
            classTimes.add(classTime5);
            classTimes.add(classTime6);
            classTimes.add(classTime7);
            classTimes.add(classTime8);
            classTimes.add(classTime9);
            classTimes.add(classTime10);
            classTimes.add(classTime11);
            classTimes.add(classTime12);
            classTimes.add(classTime13);
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
