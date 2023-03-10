package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.repository.CourseRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Resource
    private AdminService adminService;

    // 增加或更新课程前，检查教师、课程安排、容量、教师与教学楼是否符合要求
    @Override
    public ResultMessage checkBeforeAddOrUpdateCourse(Course course) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        if (course.getTeacher() == null || teacherService.findTeacherByTeacherId(course.getTeacher().getUserId()) == null
                || course.getCourseCategory() == null || course.getClassArrangements().isEmpty()) {
            return ResultMessage.FAILED;
        }
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            Classroom classroom = classroomService.findClassroomById(classArrangement.getClassroom().getClassroomId());
            if (course.getCapacity() > classroom.getCapacity() || course.getCapacity() < course.getStudents().size()) {
                return ResultMessage.WRONG_CAPACITY;
            }
            if (Boolean.FALSE.equals(commonService.isMatchBuildingAndClassroom(classArrangement.getBuilding(),classArrangement.getClassroom()))) {
                resultMessage = ResultMessage.FAILED;
                break;
            }
        }
        return resultMessage;
    }
    // 增加或更新课程前对课程安排和开放专业的准备
    @Override
    public ResultMessage prepareBeforeAddOrUpdateCourse1(Course course) {
        // 添加课程安排，由于id改变，需要重新获取
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        Set<ClassArrangement> newClassArrangement = new HashSet<>();
        for (ClassArrangement classArrangement : course.getClassArrangements()) {
            classArrangement.setClassArrangementId(0);
            if (Boolean.TRUE.equals(isConflictArrangement(classArrangement))) {
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
    @Override
    public ResultMessage prepareBeforeAddOrUpdateCourse2(Course course) {
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
    public Boolean isConflictArrangement(ClassArrangement classArrangement) {
        List<Course> courses = findCourseByTerm(adminService.getAdmin().getAcademicYear(), adminService.getAdmin().getTerm());
        List<ClassArrangement> classArrangements = new ArrayList<>();
        for (Course course : courses) {
            classArrangements.addAll(course.getClassArrangements());
        }
        for (ClassArrangement classArrangement1 : classArrangements) {
            if (!Objects.equals(classArrangement.getClassroom().getClassroomId(), classArrangement1.getClassroom().getClassroomId())
                    || classArrangement.getDayOfWeek() != classArrangement1.getDayOfWeek()) {
                continue;
            }
            if (Boolean.TRUE.equals(isConflictTime(classArrangement, classArrangement1))) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Boolean isConflictTime(ClassArrangement classArrangement, ClassArrangement classArrangement1) {
        for (ClassTime classTime : classArrangement.getClassTimes()) {
            for (ClassTime classTime1 : classArrangement1.getClassTimes()) {
                if (Objects.equals(classTime1.getClassTimeId(), classTime.getClassTimeId())) {
                    return true;
                }
            }
        }
        return false;
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
        ResultMessage resultMessage = ResultMessage.HAVE_STUDENTS;
        Course course = findCourseByCourseId(courseId);
        if (course == null) {
            return ResultMessage.NOTFOUND;
        }
        if (course.getStudents().isEmpty()){
            try {
                courseRepository.deleteById(courseId);
                resultMessage = ResultMessage.SUCCESS;
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage updateCourse(Course course) {
        Course originalCourse = findCourseByCourseId(course.getCourseId());
        if (originalCourse == null) {
            return ResultMessage.NOTFOUND;
        }
        course.setStudents(originalCourse.getStudents());
        // 更新前检查
        course.setStudents(originalCourse.getStudents());
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
                course.setNumberOfStudents(course.getStudents().size());
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
        List<Course> courses = findAllCourse();
        courses.removeIf(course -> !(Objects.equals(course.getAcademicYear(), academicYear) && Objects.equals(course.getTerm(), term)));
        return courses;
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public HashMap<String,String> batchImportCourse(MultipartFile file) {
        Course course = new Course();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"))) {
            HashMap<String,String> wrongMessage = new HashMap<>();
            String line;
            //首行列标题
            line = reader.readLine();
            while((line = reader.readLine())!= null) {
                String[] item = line.split(",");
                boolean notNullFlag = false;
                boolean formFlag = true;
                for (String i : item) {
                    if (i.length() == 0) {
                        notNullFlag = true;
                        break;
                    }
                }
                if (item.length < 8 || notNullFlag) {
                    wrongMessage.put(line, "必填项缺失！");
                    continue;
                }
                if (!item[0].matches("^[0-9]+$")) {
                    wrongMessage.put(line, "课程编号格式不正确！");
                    continue;
                }
                if (!item[1].matches("^([0-9]+)-([0-9]+)")) {
                    wrongMessage.put(line, "学年格式不正确！");
                    continue;
                }
                if (!item[2].matches("^[1|2]")) {
                    wrongMessage.put(line, "学期格式不正确！");
                    continue;
                }
                if (!item[3].matches("^[1-9][0-9+]+")) {
                    wrongMessage.put(line, "课程容量格式不正确！");
                    continue;
                }
                if (!item[7].matches("^[0-9]{8}$")) {
                    wrongMessage.put(line, "教师ID格式不正确");
                    continue;
                }

                String introduction = (item.length == 9?item[8]:"该课程暂无介绍");
                Integer courseNumber = Integer.valueOf(item[0]);
                String academicYear = item[1];
                String term = item[2];
                Integer capacity = Integer.valueOf(item[3]);
                CourseCategory courseCategory = courseCategoryService.findCourseCategoryByCourseName(item[4]);

                Building building;
                Classroom classroom;
                Teacher teacher = teacherService.findTeacherByTeacherId(Integer.valueOf(item[7]));

                HashSet<Major> openToMajors = new HashSet<>();
                HashSet<ClassArrangement> classArrangements = new HashSet<>();
                DayOfWeek dayOfWeek = null;
//
                String[] arrangements = item[6].split("/");
                String[] majors = item[5].split("/");
                // 检查课程类是否存在
                if (courseCategory == null) {
                    wrongMessage.put(line, "课程类不存在！");
                    continue;
                }
                // 检查老师是否存在
                if (teacher == null) {
                    wrongMessage.put(line, "教师不存在！");
                    continue;
                }
                // 获得开放专业
                for (String i : majors) {
                    Major major = majorService.findMajorByMajorName(i);
                    if (major == null) {
                        formFlag = false;
                        wrongMessage.put(line, i+"专业不存在！");
                        break;
                    }
                    openToMajors.add(majorService.findMajorByMajorName(i));
                }

                    // 获得课程安排
                for (String i : arrangements) {
                    HashSet<ClassTime> classTimes = new HashSet<>();
                    ClassArrangement classArrangement = new ClassArrangement();
                    Pattern a = Pattern.compile("(.{2})\\s(1[0-3]|[1-9])-(1[0-3]|[1-9])\\s([A-Z]+)(\\d+)");
                    Matcher matcher0 = a.matcher(i);
                    if (!matcher0.find()){
                        formFlag = false;
                        wrongMessage.put(line,"课程安排格式错误！");
                        break;
                    }
//                    String[] elements = i.split(" ");
                        //获得课程安排中的dayofweek
                    switch (matcher0.group(1)){
                        case "周一":dayOfWeek = DayOfWeek.MONDAY;break;
                        case "周二":dayOfWeek = DayOfWeek.TUESDAY;break;
                        case "周三":dayOfWeek = DayOfWeek.WEDNESDAY;break;
                        case "周四":dayOfWeek = DayOfWeek.THURSDAY;break;
                        case "周五":dayOfWeek = DayOfWeek.FRIDAY;break;
                        case "周六":dayOfWeek = DayOfWeek.SATURDAY;break;
                        case "周日":dayOfWeek = DayOfWeek.SUNDAY;break;
                        default:{
                            formFlag = false;
                            wrongMessage.put(line,"课程安排工作日格式不正确！");
                        }
                    }
                    // 获得上课时间
//                    Pattern time = Pattern.compile("(\\d+)-(\\d+)");
//                    Matcher matcher = time.matcher(elements[1]);
//                    classTimes.clear();
//                    if (!matcher.find()){
//                        formFlag = false;
//                        wrongMessage.put(line,"课程安排上课时间格式不正确！");
//                        break;
//                    }
                    int start = Integer.parseInt(matcher0.group(2));
                    int end = Integer.parseInt(matcher0.group(3));
//                    if (start < 1 || end > 13){
//                        formFlag = false;
//                        wrongMessage.put(line,"课程时间错误！");
//                        break;
//                    }
                    for (int j = start;j <= end;j++){
                        classTimes.add(classTimeService.findClassTimeById(j));
                    }

                    // 获得上课地点
                    classroom = classroomService.findClassroomById(Integer.valueOf(matcher0.group(5)));
                    if (classroom == null){
                        formFlag = false;
                        wrongMessage.put(line,"课程安排上课地点教室不存在！");
                        break;
                    }
                    if (classroom.getCapacity() < capacity){
                        formFlag = false;
                        wrongMessage.put(line,"课程安排课程容量超过教室容量！");
                        break;
                    }
                    building = classroom.getBuilding();

                    classArrangement.setClassroom(classroom);
                    classArrangement.setDayOfWeek(dayOfWeek);
                    classArrangement.setBuilding(building);
                    classArrangement.setClassTimes(classTimes);
//                    List<ClassArrangement> allArrangements = classArrangementService.findAllClassArrangement();
//                    for (ClassArrangement c:allArrangements){
//                        if (c.getClassroom() == classArrangement.getClassroom() && c.getDayOfWeek() == classArrangement.getDayOfWeek()){
//                            for (ClassTime t:c.getClassTimes()){
//                                for (ClassTime ti:classArrangement.getClassTimes()){
//                                    if (t == ti){
//                                        formFlag = false;
//                                        wrongMessage.put(line,"课程安排冲突!");
//                                        break;
//                                    }
//                                }
//                                if (!formFlag)break;
//                            }
//                        }
//                        if (!formFlag)break;
//                    }
                    if (this.isConflictArrangement(classArrangement)){
                        formFlag = false;
                        break;
                    }
                    classArrangements.add(classArrangement);
                }
                if (!formFlag)continue;
                course.setCourseNumber(courseNumber);
                course.setCapacity(capacity);
                course.setTeacher(teacher);
                course.setCourseCategory(courseCategory);
                course.setIntroduction(introduction);
                course.setClassArrangements(classArrangements);
                course.setAcademicYear(academicYear);
                course.setTerm(term);
                course.setOpenToMajors(openToMajors);
                this.addCourse(course);
            }
            return wrongMessage;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
