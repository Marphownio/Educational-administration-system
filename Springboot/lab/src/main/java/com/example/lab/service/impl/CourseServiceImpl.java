package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    // 增加或更新课程前，检查教师、课程安排、容量、教师与教学楼是否符合要求
    private ResultMessage checkBeforeAddOrUpdateCourse(Course course) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (course.getTeacher() == null || teacherService.findTeacherByTeacherId(course.getTeacher().getUserId()) == null
                || course.getCourseCategory() == null || course.getClassArrangements().isEmpty()) {
            return ResultMessage.FAILED;
        }
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            Classroom classroom = classroomService.findClassroomById(classArrangement.getClassroom().getClassroomId());
            if (course.getCapacity() > classroom.getCapacity()
                    || Boolean.FALSE.equals(commonService.isMatchBuildingAndClassroom(classArrangement.getBuilding(),classArrangement.getClassroom()))) {
                resultMessage = ResultMessage.FAILED;
                break;
            }
        }
        return resultMessage;
    }
    // 增加或更新课程前对课程安排和开放专业的准备
    private ResultMessage prepareBeforeAddOrUpdateCourse1(Course course) {
        // 添加课程安排，由于id改变，需要重新获取
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Set<ClassArrangement> newClassArrangement = new HashSet<>();
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            classArrangement.setApplication(null);
            classArrangement.setCourse(null);
            if (Boolean.TRUE.equals(classArrangementService.isConflictArrangement(classArrangement))) {
                resultMessage = ResultMessage.CONFLICT;
            }
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
        return resultMessage;
    }
    // 增加或更新课程前对所属课程类的准备
    private ResultMessage prepareBeforeAddOrUpdateCourse2(Course course) {
        ResultMessage resultMessage = courseCategoryService.addCourseCategory(course.getCourseCategory());
        if (resultMessage == ResultMessage.FAILED) {
            return ResultMessage.FAILED;
        }
        // 由于课程类id可能改变，需要重新获取
        CourseCategory newCourseCategory = courseCategoryService.findCourseCategoryByCourseName(course.getCourseCategory().getCourseName());
        course.setCourseCategory(newCourseCategory);
        return resultMessage;
    }

    @Override
    public ResultMessage addCourse(Course course) {
        course.setCourseId(0);
        // 添加前检查
        ResultMessage resultMessage = this.checkBeforeAddOrUpdateCourse(course);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        // 添加前准备
        ResultMessage resultMessage1 = prepareBeforeAddOrUpdateCourse1(course);
        ResultMessage resultMessage2 = prepareBeforeAddOrUpdateCourse2(course);
        if (resultMessage1 == ResultMessage.CONFLICT || resultMessage2 == ResultMessage.FAILED) {
            addCourseFailed(resultMessage2, course);
            resultMessage = (resultMessage1 == ResultMessage.CONFLICT) ? resultMessage1 : resultMessage2;
        } else {
            course.setCourseNumber(course.getCourseCategory().getCourses().size() + 1);
            course.setNumberOfStudents(0);
            try {
                courseRepository.save(course);
            }
            catch (Exception e) {
                addCourseFailed(resultMessage2, course);
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }
    private void addCourseFailed(ResultMessage resultMessage2, Course course) {
        // 由于添加课程失败，所以将课程安排删除
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
        }
        if (resultMessage2 == ResultMessage.SUCCESS) {
            // 是新增的课程类，由于添加课程失败，所以将该课程类删除
            courseCategoryService.deleteCourseCategory(course.getCourseCategory().getCourseCategoryId());
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

    @Override
    public ResultMessage updateCourse(Course course) {
        Course originalCourse = findCourseByCourseId(course.getCourseId());
        if (originalCourse == null) {
            return ResultMessage.NOTFOUND;
        }
        // 更新前检查
        ResultMessage resultMessage = checkBeforeAddOrUpdateCourse(course);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        // 清除以前课程安排
        for (ClassArrangement classArrangement : originalCourse.getClassArrangements()) {
            classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
        }
        // 更新前准备
        ResultMessage resultMessage1 = prepareBeforeAddOrUpdateCourse1(course);
        ResultMessage resultMessage2 = prepareBeforeAddOrUpdateCourse2(course);
        if (resultMessage1 == ResultMessage.CONFLICT || resultMessage2 == ResultMessage.FAILED) {
            updateCourseFailed(resultMessage2, course, originalCourse);
            resultMessage = (resultMessage1 == ResultMessage.CONFLICT) ? resultMessage1 : resultMessage2;
        } else {
            try {
                course.setCourseNumber(originalCourse.getCourseNumber());
                course.setNumberOfStudents(originalCourse.getStudents().size());
                courseRepository.save(course);
            }
            catch (Exception e) {
                updateCourseFailed(resultMessage2, course, originalCourse);
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }
    private void updateCourseFailed(ResultMessage resultMessage2, Course course, Course originalCourse) {
        // 删除改变后的课程安排
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            classArrangementService.deleteClassArrangement(classArrangement.getClassArrangementId());
        }
        // 恢复原先的课程安排
        Set<ClassArrangement> originalClassArrangements = new HashSet<>();
        for (ClassArrangement classArrangement : originalCourse.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            originalClassArrangements.add(classArrangementService.addClassArrangement(classArrangement));
        }
        originalCourse.setClassArrangements(originalClassArrangements);
        courseRepository.save(originalCourse);
        // 是新增的课程类，由于更新课程失败，所以将该课程类删除
        if (resultMessage2 == ResultMessage.SUCCESS) {
            courseCategoryService.deleteCourseCategory(course.getCourseCategory().getCourseCategoryId());
        }
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
