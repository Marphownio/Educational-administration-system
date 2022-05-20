package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ApplicationStatus;
import com.example.lab.pojo.enums.ApplicationType;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.TeacherApplicationRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherApplicationServiceImpl implements TeacherApplicationService {

    @Resource
    private TeacherApplicationRepository teacherApplicationRepository;

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private MajorService majorService;

    @Resource
    private ClassArrangementService classArrangementService;

    @Resource
    private AdminService adminService;

    @Resource
    private ClassTimeService classTimeService;

    // 教师申请增删改课程
    @Override
    public ResultMessage addTeacherApplication(TeacherApplication application) {
        application.setStatus(ApplicationStatus.IN_REVIEW);
        ResultMessage resultMessage = ResultMessage.FAILED;
        switch (application.getType()) {
            case ADD:
                application.setApplicationId(0);
                application.setCourseId(0);
                application.setCourseNumber(null);
                return applicationOfAddOrUpdateCourse(application);
            case UPDATE:
                application.setApplicationId(0);
                if (courseService.findCourseByCourseId(application.getCourseId()) == null) {
                    resultMessage = ResultMessage.NOTFOUND;
                    break;
                }
                return applicationOfAddOrUpdateCourse(application);
            case DELETE:
                resultMessage = this.applicationOfDeleteCourse(application);
        }
        return resultMessage;
    }
    private ResultMessage applicationOfAddOrUpdateCourse(TeacherApplication application) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        ResultMessage resultMessage1 = ResultMessage.FAILED;
        ResultMessage resultMessage2 = ResultMessage.FAILED;
        if (application.getType() == ApplicationType.ADD || application.getType() == ApplicationType.UPDATE) {
            resultMessage1 = this.applicationOfAddOrUpdateCourse1(application);
            resultMessage2 = this.applicationOfAddOrUpdateCourse2(application);
            if (resultMessage1 != ResultMessage.SUCCESS || resultMessage2 == ResultMessage.FAILED) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        if (resultMessage != ResultMessage.SUCCESS && (application.getType() == ApplicationType.ADD || application.getType() == ApplicationType.UPDATE)) {
            this.applicationOfAddOrUpdateCourseFailed(resultMessage2, application);
            return  (resultMessage1 == ResultMessage.CONFLICT) ? resultMessage1 : resultMessage2;
        }
        try {
            teacherApplicationRepository.save(application);
        } catch (Exception e) {
            this.applicationOfAddOrUpdateCourseFailed(resultMessage2, application);
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    private ResultMessage applicationOfAddOrUpdateCourse1(TeacherApplication application) {
        if (teacherService.findTeacherByTeacherId(application.getTeacher().getUserId()) == null) {
            return ResultMessage.FAILED;
        }
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Set<ClassArrangement> classArrangementSet = new HashSet<>();
        for (ClassArrangement classArrangement : application.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            if (Boolean.TRUE.equals(courseService.isConflictArrangement(classArrangement))) {
                resultMessage = ResultMessage.CONFLICT;
            }
            Set<ClassTime> classTimes = new HashSet<>();
            for (ClassTime classTime : classArrangement.getClassTimes()) {
                classTimes.add(classTimeService.findClassTimeById(classTime.getClassTimeId()));
            }
            classArrangement.setClassTimes(classTimes);
            classArrangementSet.add(classArrangementService.addClassArrangement(classArrangement));
        }
        application.setClassArrangements(classArrangementSet);
        Set<Major> majorSet = new HashSet<>();
        for (Major major : application.getOpenToMajors()) {
             majorSet.add(majorService.findMajorByMajorId(major.getMajorId()));
        }
        application.setOpenToMajors(majorSet);
        return resultMessage;
    }
    private ResultMessage applicationOfAddOrUpdateCourse2(TeacherApplication application) {
        CourseCategory courseCategory = courseCategoryService.findCourseCategoryByCourseName(application.getCourseCategory().getCourseName());
        if (courseCategory != null) {
            application.setCourseCategory(courseCategory);
            return ResultMessage.EXIST;
        } else {
            ResultMessage resultMessage = courseCategoryService.addCourseCategory(application.getCourseCategory());
            if (resultMessage == ResultMessage.SUCCESS) {
                application.setCourseCategory(courseCategoryService.findCourseCategoryByCourseName(application.getCourseCategory().getCourseName()));
            }
            return resultMessage;
        }
    }
    private void applicationOfAddOrUpdateCourseFailed(ResultMessage resultMessage2, TeacherApplication application) {
        if (application.getType() == ApplicationType.ADD || application.getType() == ApplicationType.UPDATE) {
            for (ClassArrangement classArrangement : application.getClassArrangements()) {
                classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
            }
        }
        // 是新增的课程类
        if (resultMessage2 == ResultMessage.SUCCESS) {
            courseCategoryService.deleteCourseCategory(application.getCourseCategory().getCourseCategoryId());
        }
    }
    private ResultMessage applicationOfDeleteCourse(TeacherApplication application) {
        Course course = courseService.findCourseByCourseId(application.getCourseId());
        if (course == null) {
            return ResultMessage.FAILED;
        }
        application.setCapacity(course.getCapacity());
        application.setIntroduction(course.getIntroduction());
        application.setCourseCategory(course.getCourseCategory());
        application.setCourseNumber(course.getCourseNumber());
        application.setOpenToMajors(course.getOpenToMajors());
        application.setClassArrangements(course.getClassArrangements());
        application.setTeacher(course.getTeacher());
        application.setStatus(ApplicationStatus.IN_REVIEW);
        return ResultMessage.SUCCESS;
    }

    // 教师取消申请
    @Override
    public ResultMessage deleteTeacherApplication(Integer applicationId) {
        if (findTeacherApplicationById(applicationId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                teacherApplicationRepository.deleteById(applicationId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateTeacherApplication(TeacherApplication application) {
        if (findTeacherApplicationById(application.getApplicationId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        try {
            teacherApplicationRepository.save(application);
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            return ResultMessage.FAILED;
        }
    }

    // 管理员获取所有申请
    @Override
    public List<TeacherApplication> findAllTeacherApplication() {
        return teacherApplicationRepository.findAll();
    }

    // 通过id查找申请
    @Override
    public TeacherApplication findTeacherApplicationById(Integer applicationId) {
        return teacherApplicationRepository.findById(applicationId).orElse(null);
    }

    // 管理员处理教师对课程的请求
    @Override
    public ResultMessage processTeacherApplication(Integer applicationId, Boolean operation) {
        TeacherApplication application = this.findTeacherApplicationById(applicationId);
        ResultMessage resultMessage = ResultMessage.FAILED;
        if (application == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else if (Boolean.FALSE.equals(operation)) {
            application.setStatus(ApplicationStatus.NOT_PASS);
            resultMessage = updateTeacherApplication(application);
        }
        if (application == null || Boolean.FALSE.equals(operation)) {
            return resultMessage;
        }
        Course course = new Course();
        Admin admin = adminService.getAdmin();
        if (application.getType() != ApplicationType.DELETE) {
            course.setCourseId(application.getCourseId());
            course.setCapacity(application.getCapacity());
            course.setIntroduction(application.getIntroduction());
            course.setCourseCategory(application.getCourseCategory());
            course.setCourseNumber(application.getCourseNumber());
            course.setAcademicYear(admin.getAcademicYear());
            course.setTerm(admin.getTerm());
            course.setOpenToMajors(application.getOpenToMajors());
            course.setTeacher(application.getTeacher());
            course.setClassArrangements(application.getClassArrangements());
        }
        switch (application.getType()) {
            case ADD:
                resultMessage = courseService.addCourse(course);
                break;
            case DELETE:
                resultMessage = courseService.deleteCourse(application.getCourseId()); break;
            case UPDATE:
                resultMessage = courseService.updateCourse(course); break;
            default:
                resultMessage = ResultMessage.FAILED; break;
        }
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        application.setStatus(ApplicationStatus.PASS);
        return updateTeacherApplication(application);
    }
}
