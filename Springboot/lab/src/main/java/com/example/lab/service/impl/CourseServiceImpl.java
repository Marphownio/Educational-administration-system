package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.CommonService;
import com.example.lab.service.CourseService;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

// 课程的增删改查服务
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private MajorService majorService;

    @Resource
    private SchoolService schoolService;

    @Resource
    private CommonService commonService;

    @Resource
    private UserService userService;

    @Override
    public ResultMessage addCourse(Course course) {
        ResultMessage resultMessage;
        if (findCourseByCourseId(course.getCourseId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if (course.getTeacher() == null || !commonService.isMatchSchoolAndMajor(course.getSchool(), course.getMajor())) {
            resultMessage = ResultMessage.FAILED;
        }
        else {
            try {
                courseRepository.save(course);
                resultMessage = ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage deleteCourse(Integer courseId) {
        if (findCourseByCourseId(courseId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                courseRepository.deleteById(courseId);
                return ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateCourse(Course course) {
        ResultMessage resultMessage;
        if (findCourseByCourseId(course.getCourseId()) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else if (course.getTeacher() == null || !commonService.isMatchSchoolAndMajor(course.getSchool(), course.getMajor())) {
            resultMessage = ResultMessage.FAILED;
        }
        else {
            try {
                courseRepository.save(course);
                resultMessage = ResultMessage.SUCCESS;
            }
            catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

    @Override
    public ResultMessage BatchImportCourse(MultipartFile file) {
        Course course = new Course();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            BufferedReader studentReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            String line;
            //首行列标题
            reader.readLine();
            studentReader.readLine();
            while((line = reader.readLine())!= null){
                String []item = line.split(",");
                if (    item[0].equals("") || item.length < 9 || item[8].equals("") || item[9].equals("")||item[8] == null || item[9] == null ||
                         !commonService.isMatchSchoolAndMajor(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])),majorService.findMajorByMajorId(Integer.valueOf(item[9])))){}
                else {
                    course.setCourseId(Integer.valueOf(item[0]));
                    course.setCourseName(item[1]);
                    course.setClassHour(Integer.valueOf(item[2]));
                    course.setCredit(Integer.valueOf(item[3]));
                    course.setCourseTime(item[4]);
                    course.setCoursePlace(item[5]);
                    course.setCapacity(item[6]);
                    course.setIntroduction(item[7]);
                    course.setMajor(majorService.findMajorByMajorId(Integer.valueOf(item[8])));
                    course.setSchool(schoolService.findSchoolBySchoolId(Integer.valueOf(item[9])));
                    course.setTeacher( userService.findTeacherByTeacherId(Integer.valueOf(item[10])));
                    if (item.length > 11){
                        String []student = item[11].split("\n");
                        for (String s : student) {
                            course.getStudents().add(userService.findStudentByStudentId(Integer.valueOf(s)));
                        }
                    }
                    courseRepository.save(course);
                }

            }
            reader.close();
            studentReader.close();
        } catch (IOException e) {
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }
}
