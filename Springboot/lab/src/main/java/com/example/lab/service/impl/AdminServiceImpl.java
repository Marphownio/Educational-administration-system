package com.example.lab.service.impl;

import com.example.lab.pojo.ApplicationType;
import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

import static com.example.lab.LabApplication.admin;
import static java.lang.Math.min;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private CourseService courseService;

    @Resource
    private TeacherApplicationService teacherApplicationService;

    // 管理员处理教师对课程的请求
    @Override
    public ResultMessage processCourseApplication(Integer applicationId, Boolean operation) {

        if (Boolean.FALSE.equals(operation)) {
            return teacherApplicationService.deleteTeacherApplication(applicationId);
        }
        TeacherApplication application = teacherApplicationService.findTeacherApplicationById(applicationId);
        if (application == null) {
            return ResultMessage.FAILED;
        }
        Course course = new Course();
        if (application.getType() != ApplicationType.DELETE) {
            course.setCourseId(application.getCourseId());

//            course.setCourseName(application.getCourseName());
//            course.setClassHour(application.getClassHour());
//            course.setCredit(application.getCredit());

            course.setClassArrangements(application.getClassArrangements());

            course.setCapacity(application.getCapacity());
            course.setIntroduction(application.getIntroduction());
//            course.setSchool(application.getSchool());
//            course.setMajor(application.getMajor());
            course.setTeacher(application.getTeacher());
        }
        ResultMessage resultMessage;
        switch (application.getType()) {
            case ADD:
                resultMessage = courseService.addCourse(course); break;
            case DELETE:
                resultMessage = courseService.deleteCourse(application.getCourseId()); break;
            case UPDATE:
                resultMessage = courseService.updateCourse(course); break;
            default:
                resultMessage = ResultMessage.FAILED; break;
        }
        if (resultMessage == ResultMessage.SUCCESS) {
            return teacherApplicationService.deleteTeacherApplication(applicationId);
        }
        else {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage changeCourseSelectionStatus() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        try {
            switch (admin.getCourseSelectionStatus()) {
                case START_TERM:    admin.setCourseSelectionStatus(CourseSelectionStatus.START_FIRST);  break;
                case START_FIRST:   admin.setCourseSelectionStatus(CourseSelectionStatus.END_FIRST);    break;
                case END_FIRST:
                    admin.setCourseSelectionStatus(CourseSelectionStatus.START_SECOND);
                    // 第一轮选课结果筛选
                    resultMessage = firstScreening();
                    break;
                case START_SECOND:  admin.setCourseSelectionStatus(CourseSelectionStatus.END_SECOND);   break;
                case END_SECOND:    admin.setCourseSelectionStatus(CourseSelectionStatus.END_TERM);     break;
                case END_TERM:
                    admin.setCourseSelectionStatus(CourseSelectionStatus.START_TERM);
                    // TODO: 学年学期自增
                    break;
            }
        }
        catch (Exception exception) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage firstScreening() {
        List<Course> courses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        List<Course> backupCourses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // TODO: 更合理的筛选
        try {
            for (Course course : courses) {
                List<Student> students = new ArrayList<>(course.getStudents());
                course.getStudents().clear();
                course.setStudents(new HashSet<>(students.subList(0, min(course.getCapacity(), students.size())-1)));
                courseService.updateCourse(course);
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
