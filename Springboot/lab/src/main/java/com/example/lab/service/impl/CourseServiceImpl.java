package com.example.lab.service.impl;

import com.example.lab.pojo.CourseSelectionStatus;
import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import static com.example.lab.LabApplication.admin;
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

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private ClassArrangementService classArrangementService;

    @Override
    public ResultMessage addCourse(Course course) {
        ResultMessage resultMessage;
        if (findCourseByCourseId(course.getCourseId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if (course.getTeacher() == null || !commonService.isMatchSchoolAndMajor(course.getCourseCategory().getSchool(), course.getCourseCategory().getMajor())) {
            resultMessage = ResultMessage.FAILED;
        } else {
            for (ClassArrangement classArrangement : course.getClassArrangements()) {
                if (course.getCapacity() > classArrangement.getClassroom().getCapacity()) {
                    return ResultMessage.FAILED;
                }
            }
            try {
                if (courseCategoryService.findCourseCategoryByCourseCategoryId(course.getCourseCategory().getCourseCategoryId()) == null) {
                   if (courseCategoryService.addCourseCategory(course.getCourseCategory()) == ResultMessage.SUCCESS) {
                       for (ClassArrangement classArrangement : course.getClassArrangements()) {
                           classArrangementService.addClassArrangement(classArrangement);
                       }
                       courseRepository.save(course);
                       resultMessage = ResultMessage.SUCCESS;

                   } else {
                       resultMessage = ResultMessage.FAILED;
                   }
                } else {
                    for (ClassArrangement classArrangement : course.getClassArrangements()) {
                        classArrangementService.addClassArrangement(classArrangement);
                    }
                    courseRepository.save(course);
                    resultMessage = ResultMessage.SUCCESS;
                }
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
        } else if (course.getTeacher() == null || !commonService.isMatchSchoolAndMajor(course.getCourseCategory().getSchool(), course.getCourseCategory().getMajor())) {
            resultMessage = ResultMessage.FAILED;
        }
        else {
            // 如果需要修改课程容量
            // 修改课程容量只有在学期开始与一轮选课期间是不需要考虑课程容量与已选人数的 其他阶段要修改容量都需要考虑
            if (course.getCapacity().equals(findCourseByCourseId(course.getCourseId()).getCapacity())
                && (admin.getCourseSelectionStatus() != CourseSelectionStatus.START_FIRST
                && admin.getCourseSelectionStatus() != CourseSelectionStatus.START_TERM)
                && course.getCapacity() < course.getStudents().size())
            {
                    return ResultMessage.FAILED;
            }
            for (ClassArrangement classArrangement : course.getClassArrangements()){
                if (course.getCapacity() > classArrangement.getClassroom().getCapacity()){
                    return ResultMessage.FAILED;
                }
            }
            try {
                if (courseCategoryService.findCourseCategoryByCourseCategoryId(course.getCourseCategory().getCourseCategoryId()) == null) {
                    if (courseCategoryService.addCourseCategory(course.getCourseCategory()) == ResultMessage.SUCCESS) {
                        for (ClassArrangement classArrangement : course.getClassArrangements()) {
                            classArrangementService.updateClassArrangement(classArrangement);
                        }
                        courseRepository.save(course);
                        resultMessage = ResultMessage.SUCCESS;

                    } else {
                        resultMessage = ResultMessage.FAILED;
                    }
                } else {
                    for (ClassArrangement classArrangement : course.getClassArrangements()) {
                        classArrangementService.updateClassArrangement(classArrangement);
                    }
                    courseRepository.save(course);
                    resultMessage = ResultMessage.SUCCESS;
                }
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
    public List<Course> findCourseByTerm(String academicYear, String term) {
        List<Course> courses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (Objects.equals(course.getAcademicYear(), academicYear) && Objects.equals(course.getTerm(), term)) {
                courses.add(course);
            }
        }
        return courses;
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

//    @Override
//    public HashMap<String,String> batchImportCourse(MultipartFile file) {
//        Course course = new Course();
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
//            BufferedReader studentReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
//            String line;
//            //首行列标题
//            reader.readLine();
//            studentReader.readLine();
//            while((line = reader.readLine())!= null){
//                String []item = line.split(",");
//                if (    item[0].equals("") || item.length < 9 || item[8].length()==0||item[9].length()==0||item[10].length()==0||
//                         !commonService.isMatchSchoolAndMajor(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])),majorService.findMajorByMajorId(Integer.valueOf(item[9])))){}
//                else {
//                    if(item[0].length()!=0&&item[1].length()!=0&&item[2].length()!=0&&item[3].length()!=0&&item[4].length()!=0&&item[5].length()!=0&&item[6].length()!=0)
//                    {
//                        boolean number=false;
//                        number=item[0].matches("^[0-9]*$")&&item[2].matches("^[0-9]*$")&&item[3].matches("^[0-9]*$");
//                        if(number)
//                        {
//                            course.setCourseId(Integer.valueOf(item[0]));
////                            course.setCourseName(item[1]);
////                            course.setClassHour(Integer.valueOf(item[2]));
////                            course.setCredit(Integer.valueOf(item[3]));
////                            course.setCapacity(item[6]);
//                            course.setIntroduction(item[7]);
////                            course.setMajor(majorService.findMajorByMajorId(Integer.valueOf(item[8])));
////                            course.setSchool(schoolService.findSchoolBySchoolId(Integer.valueOf(item[9])));
//                            course.setTeacher( userService.findTeacherByTeacherId(Integer.valueOf(item[10])));
//                            if (item.length > 11){
//                                String []student = item[11].split("\n");
//                                for (String s : student) {
//                                    course.getStudents().add(userService.findStudentByStudentId(Integer.valueOf(s)));
//                                }
//                            }
//                            courseRepository.save(course);
//                        }
//                    }
//
//                }
//
//            }
//            reader.close();
//            studentReader.close();
//        } catch (IOException e) {
//            return ResultMessage.FAILED;
//        }
//        return ResultMessage.SUCCESS;
//    }
}
