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

    // 通过id查询学生
    @Override
    public Student findStudentByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    private ResultMessage checkBeforeSelectCourse(Integer studentId, Course selectCourse) {
        ResultMessage resultMessage = ResultMessage.FAILED;
        if (selectCourse == null) {
            return ResultMessage.FAILED;
        }
        Admin admin = adminService.getAdmin();
        if (admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_SECOND) {
            return ResultMessage.NOT_OPEN;
        }
        // 是否为可选课程
        for (Course course : getSelectableCourse(studentId)) {
            if (Objects.equals(course.getCourseId(), selectCourse.getCourseId())) {
                resultMessage = ResultMessage.SUCCESS;
                break;
            }
        }
        // 二轮选课时，已选满的课程不可选
        if (resultMessage == ResultMessage.SUCCESS && admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND
                && selectCourse.getCapacity() <= selectCourse.getStudents().size()) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage selectCourse(Integer studentId, Integer courseId) {
        Course selectCourse = courseService.findCourseByCourseId(courseId);
        ResultMessage resultMessage = checkBeforeSelectCourse(studentId, selectCourse);
        if (resultMessage != ResultMessage.SUCCESS) {
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
                student.getCourses().removeIf(course1 -> Objects.equals(course1.getCourseId(),courseId));
                updateStudent(student);
                Course course = courseService.findCourseByCourseId(courseId);
                Integer updateStudentNumber = course.getNumberOfStudents();
                course.setNumberOfStudents(updateStudentNumber - 1);
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

    @Override
    public ResultMessage updateStudent(Student student) {
        if (findStudentByStudentId(student.getUserId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                studentRepository.save(student);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }
}
