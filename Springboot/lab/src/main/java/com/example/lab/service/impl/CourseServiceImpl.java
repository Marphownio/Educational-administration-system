package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.*;
//import jdk.internal.org.jline.utils.InputStreamReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.util.*;

// 课程的增删改查服务
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private ClassArrangementService classArrangementService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private MajorService majorService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private CommonService commonService;

    @Resource
    private ClassTimeService classTimeService;

    // 增加课程前，检查教师、课程安排、容量、教师与教学楼是否符合要求
    private ResultMessage checkBeforeAddCourse(Course course) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (course.getTeacher() == null || teacherService.findTeacherByTeacherId(course.getTeacher().getUserId()) == null
                || course.getCourseCategory() == null || course.getClassArrangements().isEmpty()) {
            return ResultMessage.FAILED;
        }
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            Classroom classroom = classroomService.findClassroomById(classArrangement.getClassroom().getClassroomId());
            if (course.getCapacity() > classroom.getCapacity()) {
                resultMessage = ResultMessage.FAILED;
            }
            if (Boolean.FALSE.equals(commonService.isMatchBuildingAndClassroom(classArrangement.getBuilding(),classArrangement.getClassroom()))) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }
    // 增加或更新课程前的准备
    private void prepareBeforeAddOrUpdateCourse(Course course) {
        // 添加课程安排，由于id改变，需要重新获取
        Set<ClassArrangement> newClassArrangement = new HashSet<>();
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            Set<ClassTime> classTimes = new HashSet<>();
            for (ClassTime classTime : classArrangement.getClassTimes()) {
                classTimes.add(classTimeService.findClassTimeById(classTime.getClassTimeId()));
            }
            classArrangement.setClassTimes(classTimes);
            newClassArrangement.add(classArrangementService.addClassArrangement(classArrangement));
        }
        course.setClassArrangements(newClassArrangement);
        // 找到对应的开放专业
        Set<Major> majors = new HashSet<>();
        for (Major major : course.getOpenToMajors()) {
            majors.add(majorService.findMajorByMajorId(major.getMajorId()));
        }
        course.setOpenToMajors(majors);
    }

    @Override
    public ResultMessage addCourse(Course course) {
        course.setCourseId(0);
        ResultMessage resultMessage = checkBeforeAddCourse(course);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        ResultMessage resultMessage1 = courseCategoryService.addCourseCategory(course.getCourseCategory());
        if (resultMessage1 != ResultMessage.SUCCESS && resultMessage1 != ResultMessage.EXIST) {
            return ResultMessage.FAILED;
        }
        // 由于课程类id可能改变，需要重新获取
        CourseCategory newCourseCategory = courseCategoryService.findCourseCategoryByCourseName(course.getCourseCategory().getCourseName());
        course.setCourseCategory(newCourseCategory);
        // 添加前准备
        prepareBeforeAddOrUpdateCourse(course);
        course.setCourseNumber(newCourseCategory.getCourses().size() + 1);
        course.setNumberOfStudents(0);
        try {
            courseRepository.save(course);
        }
        catch (Exception e) {
            addCourseFailed(resultMessage1, newCourseCategory, course);
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }
    private void addCourseFailed(ResultMessage resultMessage1, CourseCategory newCourseCategory, Course course) {
        if (resultMessage1 == ResultMessage.SUCCESS) {
            // 是新增的课程类，由于添加课程失败，所以将该课程类和课程安排删除
            courseCategoryService.deleteCourseCategory(newCourseCategory.getCourseCategoryId());
            for (ClassArrangement classArrangement : course.getClassArrangements()) {
                classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
            }
        }
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

    // 更新课程前的检查
    private ResultMessage checkBeforeUpdateCourse(Course course) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (findCourseByCourseId(course.getCourseId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        if (course.getTeacher() == null || teacherService.findTeacherByTeacherId(course.getTeacher().getUserId()) == null
                || course.getCourseCategory() == null || courseCategoryService.findCourseCategoryByCourseCategoryId(course.getCourseCategory().getCourseCategoryId()) == null) {
            return ResultMessage.FAILED;
        }
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            Classroom classroom = classroomService.findClassroomById(classArrangement.getClassroom().getClassroomId());
            if (course.getCapacity() > classroom.getCapacity() || Boolean.FALSE.equals(commonService.isMatchBuildingAndClassroom(classArrangement.getBuilding(),classArrangement.getClassroom()))) {
                resultMessage = ResultMessage.FAILED;
                break;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage updateCourse(Course course) {
        // 更新前检查
        ResultMessage resultMessage = checkBeforeUpdateCourse(course);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        // 清除以前课程安排
        Course getCourse = findCourseByCourseId(course.getCourseId());
        for (ClassArrangement classArrangement : getCourse.getClassArrangements()) {
            classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
        }
        // 找到对应课程类
        course.setCourseCategory(courseCategoryService.findCourseCategoryByCourseCategoryId(course.getCourseCategory().getCourseCategoryId()));

        // 更新前准备
        prepareBeforeAddOrUpdateCourse(course);
        course.setCourseNumber(findCourseByCourseId(course.getCourseId()).getCourseNumber());
        course.setNumberOfStudents(course.getStudents().size());
        try {
            courseRepository.save(course);
        }
        catch (Exception e) {
            // 删除改变后的课程安排
            for (ClassArrangement classArrangement : course.getClassArrangements()) {
                classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
            }
            // 恢复原先的课程安排
            Set<ClassArrangement> originalClassArrangements = new HashSet<>();
            for (ClassArrangement classArrangement : getCourse.getClassArrangements()) {
                classArrangement.setClassArrangementId(0);
                originalClassArrangements.add(classArrangementService.addClassArrangement(classArrangement));
            }
            getCourse.setClassArrangements(originalClassArrangements);
            courseRepository.save(getCourse);
            resultMessage = ResultMessage.FAILED;
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
//            BufferedReader arrangementReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
//            HashMap<String,String> wrongMessage = new HashMap<>();
//            String line;
//            //首行列标题
//            wrongMessage.put(reader.readLine(),"错误原因");
//            arrangementReader.readLine();
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
//                            if (item.length > 11){
//                                String []student = item[11].split("\n");
//                                for (String s : student) {
//                                    course.getStudents().add(userService.findStudentByStudentId(Integer.valueOf(s)));
//                                }
//                            }
//                            courseRepository.save(course);
//                        }
//                    }
//                }
//                Boolean flag = true;
//                for(int i = 0;i < item.length;i++) {
//                    if (item[i].length() == 0) {
//                        flag = false;
//                        break;
//                    }
//                }
//                if (item.length < 8 || !flag){
//                    wrongMessage.put(line,"必填项缺失！");
//                }
//                else {
//                    if (!item[0].matches("^[0-9]+$")){
//                        wrongMessage.put(line,"课程编号格式不正确！");
//                        continue;
//                    }
//                    if (!item[1].matches("^([1-9]+)-([1-9]+)")){
//                        wrongMessage.put(line,"学年格式不正确！");
//                        continue;
//                    }
//                    if (!item[2].matches("^[1|2]")){
//                        wrongMessage.put(line,"学期格式不正确！");
//                        continue;
//                    }
//                    if (!item[3].matches("^[1-9][0-9+]+")){
//                        wrongMessage.put(line,"课程容量格式不正确！");
//                        continue;
//                    }
//                    if (!item[7].matches("^[0-9]*$") || item[7].length() != 8){
//                        wrongMessage.put(line,"教师ID格式不正确");
//                        continue;
//                    }
//                    String []arrangement = item[6].split(" ");
//
//                }
//            }
//            reader.close();
//            return wrongMessage;
//        } catch (Exception e) {
//            return new HashMap<>();
//        }
//    }
}
