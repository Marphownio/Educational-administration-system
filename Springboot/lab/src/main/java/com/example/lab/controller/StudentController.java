package com.example.lab.controller;

import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.*;
import com.example.lab.service.CourseCategoryService;
import com.example.lab.service.CourseService;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private UserService userService;

    // 查询全部学生
    @GetMapping(value = "/list")
    public ResponseEntity<Set<Student>> findAllStudent() {
        Set<Student> students = new HashSet<>(userService.findAllStudent());
        if (students.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 学生选课
    @PostMapping(value = "/course/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
            try {
                Student currentUser = userService.findStudentByStudentId(((Student) session.getAttribute("user")).getUserId());
                Course selectCourse = courseService.findCourseByCourseId(courseId);

                ResponseEntity<Set<Course>> courses = findAllCoursesStudying(currentUser.getUserId());
                Set<Course> courseSet = courses.getBody();
                if (courseSet != null && !courseSet.isEmpty()) {
                    for (Course course : courseSet) {
                        // 已经选过同一类课程
                        if (course.getCourseCategory() == selectCourse.getCourseCategory()) {
                            return ResultMessage.EXIST;
                        }
                    }
                }
                selectCourse.getStudents().add(currentUser);
                courseService.updateCourse(selectCourse);
                return ResultMessage.SUCCESS;
            } catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
        else {
            return ResultMessage.NOT_OPEN;
        }
    }

    // 学生获取可选的课程
    @GetMapping(value = "/course/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND
                && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRole() == UserRole.STUDENT) {
            Student currentUser =  userService.findStudentByStudentId(((User)session.getAttribute("user")).getUserId());
            Set<Course> selectableCourses = new HashSet<>();
            // 本专业可选的课程种类
            for (CourseCategory courseCategory : currentUser.getMajor().getSelectableCourseCategories()) {
                selectableCourses.addAll(courseCategoryService.findCourseByTermInCourseCategory(courseCategory, admin.getAcademicYear(), admin.getTerm()));
            }
            if (selectableCourses.isEmpty()) {
                return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
            } else {
                // 去除已选/已修的课程
                selectableCourses.removeIf(course -> !currentUser.getCourses().contains(course));
                // 二轮选课，去除已经选满的课程
                if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
                    selectableCourses.removeIf(course -> course.getCapacity() == course.getStudents().size());
                }
                if (selectableCourses.isEmpty()) {
                    return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(selectableCourses, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 查看指定学生所有已修课程
    @GetMapping(value = "/courses/completed")
    public ResponseEntity<Set<Course>> findAllCoursesCompleted(@RequestParam(value = "studentId") Integer studentId) {
        Student student = userService.findStudentByStudentId(studentId);
        Set<Course> courses = new HashSet<>();
        if (student == null) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        courses.addAll(student.getCourses());
        courses.removeIf(course -> Objects.equals(course.getAcademicYear(), admin.getAcademicYear()) || Objects.equals(course.getTerm(), admin.getTerm()));
        if (courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // 查看指定学生所有已选/在读课程
    @GetMapping(value = "/courses/studying")
    public ResponseEntity<Set<Course>> findAllCoursesStudying(@RequestParam(value = "studentId") Integer studentId) {
        Student student = userService.findStudentByStudentId(studentId);
        Set<Course> courses = new HashSet<>();
        if (student == null) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        courses.addAll(student.getCourses());
        courses.removeIf(course -> !Objects.equals(course.getAcademicYear(), admin.getAcademicYear()) || !Objects.equals(course.getTerm(), admin.getTerm()));
        if (courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
