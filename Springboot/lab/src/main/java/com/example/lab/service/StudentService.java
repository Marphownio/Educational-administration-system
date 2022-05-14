package com.example.lab.service;

import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.enums.ResultMessage;

import java.util.List;
import java.util.Set;

public interface StudentService {

    Student findStudentByStudentId(Integer studentId);

    List<Student> findAllStudent();

    ResultMessage selectCourse(Integer studentId, Integer courseId);

    Set<Course> getSelectableCourse(Integer studentId);

    ResultMessage dropCourse(Integer studentId, Integer courseId);

    Set<Course> findAllCoursesStudying(Integer studentId);

    Set<Course> findAllCoursesSpecified(Integer studentId, String academicYear, String term);

    Set<Course> findAllCoursesCompleted(Integer studentId);

    ResultMessage updateStudent(Student student);
}
