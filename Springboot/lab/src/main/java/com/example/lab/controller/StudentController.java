package com.example.lab.controller;

import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.CourseCategory;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.CourseCategoryService;
import com.example.lab.service.CourseService;
import com.example.lab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
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
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 学生选课
    @PostMapping(value = "/course/select")
    public ResultMessage selectCourse(@RequestParam("courseId") Integer courseId, HttpSession session) {
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
            try {
                Student currentUser = userService.findStudentByStudentId(((Student) session.getAttribute("user")).getUserId());
                Course course = courseService.findCourseByCourseId(courseId);
                course.getStudents().add(currentUser);
                courseService.updateCourse(course);
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
            for (CourseCategory courseCategory : currentUser.getMajor().getSelectableCourseCategories()) {
                selectableCourses.addAll(courseCategoryService.findCourseByTermInCourseCategory(courseCategory, admin.getAcademicYear(), admin.getTerm()));
            }
            if (selectableCourses.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                selectableCourses.removeIf(course -> !currentUser.getCourses().contains(course));
                if (selectableCourses.isEmpty()) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(selectableCourses, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
