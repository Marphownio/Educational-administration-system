package com.example.lab.service.impl;

import com.example.lab.pojo.entity.Admin;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.StudentRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private AdminService adminService;

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    // 通过id查询学生
    @Override
    public Student findStudentByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public ResultMessage selectCourse(Integer studentId, Integer courseId) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Admin admin = adminService.getAdmin();
        if (admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_SECOND) {
            resultMessage = ResultMessage.NOT_OPEN;
        }
        Course selectCourse = courseService.findCourseByCourseId(courseId);
        // 二轮选课时，已选满的课程不可选
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND && selectCourse.getCapacity() <= selectCourse.getStudents().size()) {
            resultMessage = ResultMessage.FAILED;
        }
        if (resultMessage == ResultMessage.NOT_OPEN || resultMessage == ResultMessage.FAILED) {
            return resultMessage;
        }
        Set<Course> courseSet = findAllCoursesStudying(studentId);
        if (courseSet != null && !courseSet.isEmpty()) {
            for (Course course : courseSet) {
                // 已经选过同类课程
                if (course.getCourseCategory() == selectCourse.getCourseCategory()) {
                    return ResultMessage.EXIST;
                }
            }
        }
        Student student = findStudentByStudentId(studentId);
        selectCourse.getStudents().add(student);
        return courseService.updateCourse(selectCourse);
    }

    @Override
    public Set<Course> getSelectableCourse(Integer studentId) {
        Student student = findStudentByStudentId(studentId);
        // 本专业可选课程(已选课程的同类课程依然会出现，但在选择时不会通过)
        Set<Course> selectableCourses = student.getMajor().getSelectableCourses();
        if (selectableCourses.isEmpty()) {
            return new HashSet<>();
        } else {
            // 去除已选/已修的课程
            selectableCourses.removeIf(course -> student.getCourses().contains(course));
            return selectableCourses;
        }
    }

    @Override
    public ResultMessage dropCourse(Integer studentId, Integer courseId) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Student student = findStudentByStudentId(studentId);
        Admin admin = adminService.getAdmin();
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND){
            try {
                Course course = courseService.findCourseByCourseId(courseId);
                course.getStudents().remove(student);
                student.getCourses().remove(course);
                userService.updateUser(student);
                courseService.updateCourse(course);
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        else {
            resultMessage = ResultMessage.NOT_OPEN;
        }
        return resultMessage;
    }

    @Override
    public Set<Course> findAllCoursesStudying(Integer studentId) {
        Admin admin = adminService.getAdmin();
        return findAllCoursesSpecified(studentId, admin.getAcademicYear(), admin.getTerm());
    }

    @Override
    public Set<Course> findAllCoursesSpecified(Integer studentId, String academicYear, String term) {
        Student student = findStudentByStudentId(studentId);
        Set<Course> courses = new HashSet<>();
        if (student == null) {
            return courses;
        }
        courses.addAll(student.getCourses());
        courses.removeIf(course -> !Objects.equals(course.getAcademicYear(), academicYear) && Objects.equals(course.getTerm(), term));
        return courses;
    }

    @Override
    public Set<Course> findAllCoursesCompleted(Integer studentId) {
        Set<Course> courses = new HashSet<>();
        Student student = findStudentByStudentId(studentId);
        if (student == null) {
            return courses;
        }
        courses.addAll(student.getCourses());
        Admin admin = adminService.getAdmin();
        courses.removeIf(course -> Objects.equals(course.getAcademicYear(), admin.getAcademicYear()) && Objects.equals(course.getTerm(), admin.getTerm()));
        return courses;
    }
}
