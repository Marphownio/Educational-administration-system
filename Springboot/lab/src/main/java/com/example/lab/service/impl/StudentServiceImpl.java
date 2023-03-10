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

    @Override
    public ResultMessage checkBeforeSelectCourse(Integer studentId, Course selectCourse) {
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
        // 二轮选课时
        if (resultMessage == ResultMessage.SUCCESS && admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
            resultMessage = checkBeforeSelectCourse2(studentId, selectCourse);
        }
        return resultMessage;
    }
    @Override
    public ResultMessage checkBeforeSelectCourse2(Integer studentId, Course selectCourse) {
        // 二轮选课时，已选满的课程不可选
        if (selectCourse.getCapacity() <= selectCourse.getStudents().size()) {
            return ResultMessage.FAILED;
        }
        // 二轮课程安排冲突，不可选
        Set<Course> courseSet = findAllCoursesStudying(studentId);
        for (Course course : courseSet) {
            if (Boolean.TRUE.equals(isArrangementConflict(course, selectCourse))) {
                return ResultMessage.FAILED;
            }
        }
        return ResultMessage.SUCCESS;
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
                if (Objects.equals(course.getCourseCategory().getCourseCategoryId(), selectCourse.getCourseCategory().getCourseCategoryId())) {
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
        if (admin.getCourseSelectionStatus() == CourseSelectionStatus.START_FIRST || admin.getCourseSelectionStatus() == CourseSelectionStatus.START_SECOND) {
            try {
                student.getCourses().removeIf(course1 -> Objects.equals(course1.getCourseId(), courseId));
                updateStudent(student);
                Course course = courseService.findCourseByCourseId(courseId);
                Integer updateStudentNumber = course.getNumberOfStudents();
                course.setNumberOfStudents(updateStudentNumber - 1);
                courseService.updateCourse(course);
            } catch (Exception e) {
                resultMessage = ResultMessage.FAILED;
            }
        } else {
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
        } else {
            try {
                studentRepository.save(student);
                return ResultMessage.SUCCESS;
            } catch (Exception exception) {
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
                case START_FIRST:   admin.setCourseSelectionStatus(CourseSelectionStatus.END_FIRST);
                    // 第一轮选课结果筛选
                    resultMessage = firstScreening(); break;
                case END_FIRST:     admin.setCourseSelectionStatus(CourseSelectionStatus.START_SECOND); break;
                case START_SECOND:  admin.setCourseSelectionStatus(CourseSelectionStatus.END_SECOND);   break;
                case END_SECOND:    admin.setCourseSelectionStatus(CourseSelectionStatus.END_TERM);     break;
                case END_TERM:      admin.setCourseSelectionStatus(CourseSelectionStatus.START_TERM);   break;
            }
            if (resultMessage == ResultMessage.SUCCESS) {
                resultMessage = adminService.saveAdmin(admin);
            }
        } catch (Exception e) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage firstScreening() {
        // 课程时间冲突
        if (firstScreeningArrangementConflict() != ResultMessage.SUCCESS) {
            return ResultMessage.FAILED;
        }
        Admin admin = adminService.getAdmin();
        // 选课人数超课程容量
        List<Course> courses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // 备份，失败时回滚
        List<Course> backupCourses = courseService.findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        for (Course course : courses) {
            List<Student> students = new ArrayList<>(course.getStudents());
            if (!students.isEmpty()) {
                course.getStudents().clear();
                course.setStudents(new HashSet<>(randomKick(students, course.getCapacity())));
            }
            try {
                course.setNumberOfStudents(course.getStudents().size());
                courseRepository.save(course);
            } catch (Exception e) {
                courseRepository.saveAll(backupCourses);
                return ResultMessage.FAILED;
            }
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage firstScreeningArrangementConflict() {
        List<Student> studentList = findAllStudent();
        studentList.removeIf(student -> !student.getStatus());
        for (Student student : studentList) {
            removeConflictCourse(student);
            try {
                studentRepository.save(student);
            } catch (Exception e) {
                studentRepository.saveAll(studentList);
                return ResultMessage.FAILED;
            }
        }
        return ResultMessage.SUCCESS;
    }
    @Override
    public void removeConflictCourse(Student student) {
        Set<Course> courseSet = findAllCoursesStudying(student.getUserId());
        Set<Course> courseSet1 = findAllCoursesCompleted(student.getUserId());
        List<Course> courseList = new ArrayList<>(courseSet);
        for (int i = 0; i < courseList.size() - 1; i++) {
            Course course1 = courseList.get(i);
            for (int j = i + 1; j < courseList.size(); j++) {
                Course course2 = courseList.get(j);
                if (Boolean.TRUE.equals(isArrangementConflict(course1, course2)) && Boolean.TRUE.equals(isContains(course1, course2, courseSet))) {
                    courseSet.removeIf(course -> Objects.equals(course.getCourseId(), course2.getCourseId()));
                }
            }
        }
        student.setCourses(courseSet1);
        student.getCourses().addAll(courseSet);
    }
    @Override
    public Boolean isContains(Course course1, Course course2, Set<Course> courseSet) {
        int count = 0;
        for (Course course : courseSet) {
            if (Objects.equals(course.getCourseId(), course1.getCourseId()) || Objects.equals(course.getCourseId(), course2.getCourseId())) {
                count++;
            }
        }
        return count == 2;
    }
    @Override
    public Boolean isArrangementConflict(Course course1, Course course2) {
        Set<ClassArrangement> classArrangementSet1 = course1.getClassArrangements();
        Set<ClassArrangement> classArrangementSet2 = course2.getClassArrangements();
        for (ClassArrangement classArrangement1 : classArrangementSet1) {
            for (ClassArrangement classArrangement2 : classArrangementSet2) {
                if (classArrangement1.getDayOfWeek() != classArrangement2.getDayOfWeek()) {
                    continue;
                }
                if (Boolean.TRUE.equals(isTimeConflict(classArrangement1, classArrangement2))) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Boolean isTimeConflict(ClassArrangement classArrangement1, ClassArrangement classArrangement2) {
        for (ClassTime classTime1 : classArrangement1.getClassTimes()) {
            for (ClassTime classTime2 : classArrangement2.getClassTimes()) {
                if (Objects.equals(classTime1.getClassTimeId(), classTime2.getClassTimeId())) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public List<Student> randomKick(List<Student> students, Integer capacity) {
        // 按学号从小到大排序
        Collections.sort(students);
        // 按年级分成多个HashSet, 年级有序，HashSet内同一年纪的学生无序，实现优先高年级，同一年级随机
        List<Set<Student>> sortedStudents = new ArrayList<>();
        sortedStudents.add(new HashSet<>());
        for (int i = 0; i < students.size() - 1; i++) {
            Student student1 = students.get(i);
            Student student2 = students.get(i + 1);
            sortedStudents.get(sortedStudents.size()-1).add(student1);
            if (student1.getUserId() / 10000 != student2.getUserId() / 10000) {
                sortedStudents.add(new HashSet<>());
            }
        }
        sortedStudents.get(sortedStudents.size()-1).add(students.get(students.size()-1));
        // 截取合适数量的学生
        List<Student> resultStudents = new ArrayList<>();
        for (Set<Student> studentSet : sortedStudents) {
            resultStudents.addAll(studentSet);
        }
        return resultStudents.subList(0, min(capacity, students.size()));
    }
}
