package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
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
    private UserService userService;

    @Resource
    private MajorService majorService;

    @Resource
    private ClassArrangementService classArrangementService;

    @Resource
    private AdminService adminService;

    // 教师申请增删改课程
    @Override
    public ResultMessage addTeacherApplication(TeacherApplication application) {
        ResultMessage resultMessage;
        switch (application.getType()) {
            case ADD:
                application.setCourseId(0);
                resultMessage = this.applicationOfAddOrUpdateCourse(application); break;
            case UPDATE:
                if (courseService.findCourseByCourseId(application.getCourseId()) == null) {
                    resultMessage = ResultMessage.FAILED;
                    break;
                }
                resultMessage = this.applicationOfAddOrUpdateCourse(application); break;
            case DELETE:
                resultMessage = this.applicationOfDeleteCourse(application); break;
            default:
                resultMessage = ResultMessage.NOTFOUND;
        }
        if (resultMessage == ResultMessage.SUCCESS) {
            try {
                teacherApplicationRepository.save(application);
            } catch (Exception e) {
                this.applicationOfAddOrUpdateCourseFailed(application);
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    private ResultMessage applicationOfAddOrUpdateCourse(TeacherApplication application) {
        if (userService.findTeacherByTeacherId(application.getTeacher().getUserId()) == null) {
            return ResultMessage.FAILED;
        }
        Set<ClassArrangement> classArrangementSet = new HashSet<>();
        for (ClassArrangement classArrangement : application.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            classArrangementSet.add(classArrangementService.addClassArrangement(classArrangement));
        }
        application.setClassArrangements(classArrangementSet);
        Set<Major> majorSet = new HashSet<>();
        for (Major major : application.getOpenToMajors()) {
             majorSet.add(majorService.findMajorByMajorId(major.getMajorId()));
        }
        application.setOpenToMajors(majorSet);
        if (courseCategoryService.findCourseCategoryByCourseCategoryId(application.getCourseCategory().getCourseCategoryId()) != null) {
            application.setCourseCategory(courseCategoryService.findCourseCategoryByCourseCategoryId(application.getCourseCategory().getCourseCategoryId()));
        } else {
            return courseCategoryService.addCourseCategory(application.getCourseCategory());
        }
        return ResultMessage.SUCCESS;
    }

    private void applicationOfAddOrUpdateCourseFailed(TeacherApplication application) {
        if (application.getType() == ApplicationType.ADD || application.getType() == ApplicationType.UPDATE) {
            for (ClassArrangement classArrangement : application.getClassArrangements()) {
                classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
            }
        }
        if (application.getCourseCategory().getCourses().isEmpty()) {
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
        application.setOpenToMajors(course.getOpenToMajors());
        application.setClassArrangements(course.getClassArrangements());
        application.setTeacher(course.getTeacher());
        return ResultMessage.SUCCESS;
    }

    // 教师取消申请或管理员处理完申请后将其删除
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
        if (Boolean.FALSE.equals(operation)) {
            return this.deleteTeacherApplication(applicationId);
        }
        TeacherApplication application = this.findTeacherApplicationById(applicationId);
        if (application == null) {
            return ResultMessage.FAILED;
        }
        Course course = new Course();
        Admin admin = adminService.getAdmin();
        if (application.getType() != ApplicationType.DELETE) {
            course.setCourseId(application.getCourseId());
            course.setAcademicYear(admin.getAcademicYear());
            course.setTerm(admin.getTerm());
            course.setCapacity(application.getCapacity());
            course.setIntroduction(application.getIntroduction());
            course.setCourseCategory(application.getCourseCategory());
            course.setOpenToMajors(application.getOpenToMajors());
            course.setClassArrangements(application.getClassArrangements());
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
        System.out.println(resultMessage);
        return (resultMessage == ResultMessage.SUCCESS) ? this.deleteTeacherApplication(applicationId) : ResultMessage.FAILED;
    }
}
