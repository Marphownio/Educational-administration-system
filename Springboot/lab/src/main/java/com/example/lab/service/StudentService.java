package com.example.lab.service;

import com.example.lab.pojo.entity.ClassArrangement;
import com.example.lab.pojo.entity.Course;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.enums.ResultMessage;

import java.util.List;
import java.util.Set;

public interface StudentService {

    Student findStudentByStudentId(Integer studentId);

    List<Student> findAllStudent();

    ResultMessage checkBeforeSelectCourse(Integer studentId, Course selectCourse);

    ResultMessage checkBeforeSelectCourse2(Integer studentId, Course selectCourse);

    ResultMessage selectCourse(Integer studentId, Integer courseId);

    Set<Course> getSelectableCourse(Integer studentId);

    ResultMessage dropCourse(Integer studentId, Integer courseId);

    Set<Course> findAllCoursesStudying(Integer studentId);

    Set<Course> findAllCoursesSpecified(Integer studentId, String academicYear, String term);

    Set<Course> findAllCoursesCompleted(Integer studentId);

    ResultMessage updateStudent(Student student);

    ResultMessage changeCourseSelectionStatus();

    ResultMessage firstScreening();

    ResultMessage firstScreeningArrangementConflict();

    void removeConflictCourse(Student student);

    Boolean isContains(Course course1, Course course2, Set<Course> courseSet);

    Boolean isArrangementConflict(Course course1, Course course2);

    Boolean isTimeConflict(ClassArrangement classArrangement1, ClassArrangement classArrangement2);

    List<Student> randomKick(List<Student> students, Integer capacity);
}
