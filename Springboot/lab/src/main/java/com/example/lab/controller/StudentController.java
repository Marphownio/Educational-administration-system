package com.example.lab.controller;

import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.enums.UserRole;
import com.example.lab.pojo.entity.*;
import com.example.lab.service.AdminService;
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

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

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
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Admin admin = adminService.getAdmin();
        if (admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_SECOND) {
            resultMessage = ResultMessage.NOT_OPEN;
        }
        Student currentUser = userService.findStudentByStudentId(((User) session.getAttribute("user")).getUserId());
        Course selectCourse = courseService.findCourseByCourseId(courseId);
        // 二轮选课时，已选满的课程不可选
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND && selectCourse.getCapacity() <= selectCourse.getStudents().size()) {
            resultMessage = ResultMessage.FAILED;
        }
        if (resultMessage == ResultMessage.NOT_OPEN || resultMessage == ResultMessage.FAILED) {
            return resultMessage;
        }
        ResponseEntity<Set<Course>> courses = findAllCoursesStudying(session);
        Set<Course> courseSet = courses.getBody();
        if (courseSet != null && !courseSet.isEmpty()) {
            for (Course course : courseSet) {
                // 已经选过同类课程
                if (course.getCourseCategory() == selectCourse.getCourseCategory()) {
                    return ResultMessage.EXIST;
                }
            }
        }
        selectCourse.getStudents().add(currentUser);
        return courseService.updateCourse(selectCourse);
    }

    // 学生退课
    @DeleteMapping(value = "/course/drop")
    public ResultMessage dropCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return ResultMessage.FAILED;
        }
        Admin admin = adminService.getAdmin();
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND){
            try {
                Student currentUser = userService.findStudentByStudentId(user.getUserId());
                Course course = courseService.findCourseByCourseId(courseId);
                course.getStudents().remove(currentUser);
                courseService.updateCourse(course);
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        } else {
            resultMessage = ResultMessage.NOT_OPEN;
        }
        return resultMessage;
    }

    // 学生获取可选的课程
    @GetMapping(value = "/course/selectable")
    public ResponseEntity<Set<Course>> getSelectableCourse(HttpSession session) {
        Admin admin = adminService.getAdmin();
        if ((admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_SECOND)
                || session.getAttribute("user") == null || ((User)session.getAttribute("user")).getRole() != UserRole.STUDENT) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NOT_ACCEPTABLE);
        }
        Student currentUser =  userService.findStudentByStudentId(((User)session.getAttribute("user")).getUserId());
        // 本专业可选课程(已选课程的同类课程依然会出现，但在选择时不会通过)
        Set<Course> selectableCourses = currentUser.getMajor().getSelectableCourses();
        if (selectableCourses.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NO_CONTENT);
        } else {
            // 去除已选/已修的课程
            selectableCourses.removeIf(course -> currentUser.getCourses().contains(course));
            return new ResponseEntity<>(selectableCourses, HttpStatus.OK);
        }
    }

    // 学生查看所有已修课程
    @GetMapping(value = "/courses/completed/all")
    public ResponseEntity<Set<Course>> findAllCoursesCompleted(HttpSession session) {
        Set<Course> courses = new HashSet<>();
        User user = (User)session.getAttribute("user");
        Student student = userService.findStudentByStudentId(user.getUserId());
        if (student == null) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        courses.addAll(student.getCourses());
        Admin admin = adminService.getAdmin();
        courses.removeIf(course -> Objects.equals(course.getAcademicYear(), admin.getAcademicYear()) || Objects.equals(course.getTerm(), admin.getTerm()));
        if (courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // 学生查看在指定学年学期的课程
    @GetMapping(value = "/courses/specified")
    public ResponseEntity<Set<Course>> findAllCoursesSpecified(@RequestParam(value = "academicYear") String academicYear,
                                                               @RequestParam(value = "term") String term, HttpSession session) {
        User user = (User)session.getAttribute("user");
        Student student = userService.findStudentByStudentId(user.getUserId());
        Set<Course> courses = new HashSet<>();
        if (student == null) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        courses.addAll(student.getCourses());
        courses.removeIf(course -> Objects.equals(course.getAcademicYear(), academicYear) || Objects.equals(course.getTerm(), term));
        if (courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // 学生查看所有已选/在读课程
    @GetMapping(value = "/courses/studying")
    public ResponseEntity<Set<Course>> findAllCoursesStudying(HttpSession session) {
        Admin admin = adminService.getAdmin();
        return findAllCoursesSpecified(admin.getAcademicYear(), admin.getTerm(), session);
    }
}
