package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.CourseSelectionStatus;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.repository.StudentRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private CourseRepository courseRepository;

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
        if (resultMessage == ResultMessage.SUCCESS && admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
            // 二轮时间冲突，不可选
            Set<Course> courseSet = findAllCoursesStudying(studentId);
            for (Course course : courseSet) {
                if (Boolean.TRUE.equals(isTimeConflict(course, selectCourse))) {
                    resultMessage = ResultMessage.FAILED;
                    break;
                }
            }
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
        try {
            selectCourse.setNumberOfStudents(selectCourse.getNumberOfStudents() + 1);
            courseRepository.save(selectCourse);
        } catch (Exception e) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public Set<Course> getSelectableCourse(Integer studentId) {
        Student student = findStudentByStudentId(studentId);
        // 本专业可选课程(已选课程的同类课程依然会出现，但在选择时不会通过)
        Set<Course> selectableCourses = student.getMajor().getSelectableCourses();
        if (selectableCourses.isEmpty()) {
            return new HashSet<>();
        } else {
            // 去除已选/已修的课程,去除以往学期的课程
            selectableCourses.removeIf(course -> student.getCourses().contains(course)
                    || !(Objects.equals(course.getAcademicYear(), adminService.getAdmin().getAcademicYear()) && Objects.equals(course.getTerm(), adminService.getAdmin().getTerm())));
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
        courses.removeIf(course -> !(Objects.equals(course.getAcademicYear(), academicYear) && Objects.equals(course.getTerm(), term)));
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

    @Override
    public ResultMessage changeCourseSelectionStatus() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Admin admin = adminService.getAdmin();
        try {
            switch (admin.getCourseSelectionStatus()) {
                case START_TERM:    admin.setCourseSelectionStatus(CourseSelectionStatus.START_FIRST);  break;
                case START_FIRST:   admin.setCourseSelectionStatus(CourseSelectionStatus.END_FIRST);    break;
                case END_FIRST:     admin.setCourseSelectionStatus(CourseSelectionStatus.START_SECOND);
                    // 第一轮选课结果筛选
                    resultMessage = firstScreening(); break;
                case START_SECOND:  admin.setCourseSelectionStatus(CourseSelectionStatus.END_SECOND);   break;
                case END_SECOND:    admin.setCourseSelectionStatus(CourseSelectionStatus.END_TERM);     break;
                case END_TERM:      admin.setCourseSelectionStatus(CourseSelectionStatus.START_TERM);   break;
            }
            if (resultMessage == ResultMessage.SUCCESS) {
                resultMessage = adminService.saveAdmin(admin);
            }
        }
        catch (Exception e) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage firstScreening() {
        Admin admin = adminService.getAdmin();
        List<Course> courses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // 备份，失败时回滚
        List<Course> backupCourses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());

        // 课程时间冲突
        for (Student student : findAllStudent()) {
            Set<Course> courseSet = findAllCoursesStudying(student.getUserId());
            for (Course course1 : courseSet) {
                for (Course course2 : courseSet) {
                    if (Objects.equals(course1.getCourseId(), course2.getCourseId())) {
                        continue;
                    }
                    if (Boolean.TRUE.equals(isTimeConflict(course1, course2))) {
                        courseSet.removeIf(course -> Objects.equals(course.getCourseId(), course2.getCourseId()));
                    }
                }
            }
            student.setCourses(courseSet);
            updateStudent(student);
        }

        // 选课人数超课程容量
        for (Course course : courses) {
            List<Student> students = new ArrayList<>(course.getStudents());
            Collections.sort(students);
            course.getStudents().clear();
            course.setStudents(new HashSet<>(students.subList(0, max(min(course.getCapacity(), students.size()) - 1, 0))));
            if (courseService.updateCourse(course) == ResultMessage.SUCCESS) {
                continue;
            }
            for (Course course1 : backupCourses) {
                courseService.updateCourse(course1);
            }
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;

    }
    private Boolean isTimeConflict(Course course1, Course course2) {
        Set<ClassArrangement> classArrangementSet1 = course1.getClassArrangements();
        Set<ClassArrangement> classArrangementSet2 = course2.getClassArrangements();
        for (ClassArrangement classArrangement1 : classArrangementSet1) {
            for (ClassArrangement classArrangement2 : classArrangementSet2) {
                if (classArrangement1.getDayOfWeek() != classArrangement2.getDayOfWeek()) {
                    continue;
                }
                for (ClassTime classTime1 : classArrangement1.getClassTimes()) {
                    for (ClassTime classTime2 : classArrangement2.getClassTimes()) {
                        if (Objects.equals(classTime1.getClassTimeId(), classTime2.getClassTimeId())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
