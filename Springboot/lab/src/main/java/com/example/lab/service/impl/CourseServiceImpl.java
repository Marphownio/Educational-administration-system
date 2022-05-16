package com.example.lab.service.impl;

import com.example.lab.pojo.entity.*;
import com.example.lab.pojo.enums.CourseSelectionStatus;
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

import static java.lang.Math.max;
import static java.lang.Math.min;

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
            for (ClassTime classTime : classArrangement.getClassTimes()) {
                for (ClassTime classTime1 : classArrangement1.getClassTimes()) {
                    if (Objects.equals(classTime1.getClassTimeId(), classTime.getClassTimeId())) {
                        return true;
                    }
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
        List<Course> courses = findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // 备份，失败时回滚
        List<Course> backupCourses = findCourseByTerm(admin.getAcademicYear(), admin.getTerm());
        // TODO: 更合理的筛选, 课程时间冲突，模块复选
        for (Course course : courses) {
            List<Student> students = new ArrayList<>(course.getStudents());
            course.getStudents().clear();
            course.setStudents(new HashSet<>(students.subList(0, max(min(course.getCapacity(), students.size()) - 1, 0))));
            if (updateCourse(course) == ResultMessage.SUCCESS) {
                continue;
            }
            for (Course course1 : backupCourses) {
                updateCourse(course1);
            }
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }


    @Override
    public HashMap<String,String> batchImportCourse(MultipartFile file) {
        Course course = new Course();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            HashMap<String,String> wrongMessage = new HashMap<>();
            String line;
            //首行列标题
            wrongMessage.put(reader.readLine(),"错误原因");
            while((line = reader.readLine())!= null) {
                String[] item = line.split(",");
                System.out.println(line);
                boolean notNullFlag = true;
                boolean formFlag = true;
                for (String i : item) {
                    if (i.length() == 0) {
                        notNullFlag = false;
                        break;
                    }
                }
                if (item.length < 8 || Boolean.FALSE.equals(notNullFlag)) {
                    wrongMessage.put(line, "必填项缺失！");
                } else {
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
                    if (!item[7].matches("^[0-9]*$") || item[7].length() != 8) {
                        wrongMessage.put(line, "教师ID格式不正确");
                        continue;
                    }

                    String introduction = "该课程暂无介绍";
                    if (item.length == 9) {
                        introduction = item[8];
                    }
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
                        if (major != null) {
                            openToMajors.add(majorService.findMajorByMajorName(i));
                        } else {
                            formFlag = false;
                            wrongMessage.put(line, i+"专业不存在！");
                            break;
                        }
                    }
                    if (!formFlag) continue;

                    // 获得课程安排
                    for (String i : arrangements) {
                        HashSet<ClassTime> classTimes = new HashSet<>();
                        ClassArrangement classArrangement = new ClassArrangement();
                        if (!i.matches("(.+)\\s(.+)\\s(.+)")){
                            formFlag = false;
                            wrongMessage.put(line,"课程安排格式错误！");
                            break;
                        }
                        String[] elements = i.split(" ");
                        if (elements.length < 3) {
                            formFlag = false;
                            wrongMessage.put(line, "课程安排数据缺失!");
                            break;
                        } else {
                            //获得课程安排中的dayofweek
                            switch (elements[0]){
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
                            if (!formFlag)break;

                            // 获得上课时间
                            Pattern time = Pattern.compile("(\\d+)-(\\d+)");
                            Matcher matcher = time.matcher(elements[1]);
                            classTimes.clear();
                            if (matcher.find()){
                                int start = Integer.parseInt(matcher.group(1));
                                int end = Integer.parseInt(matcher.group(2));
                                if (start < 1 || end > 13){
                                    formFlag = false;
                                    wrongMessage.put(line,"课程时间错误！");
                                    break;
                                }
                                for (int j = start;j <= end;j++){
                                    classTimes.add(classTimeService.findClassTimeById(j));
                                }
                            }
                            else {
                                formFlag = false;
                                wrongMessage.put(line,"课程安排上课时间格式不正确！");
                                break;
                            }

                            // 获得上课地点
                            Pattern place = Pattern.compile("([A-Z]+)(\\d+)");
                            Matcher matcher1 = place.matcher(elements[2]);
                            if (matcher1.find()){
                                classroom = classroomService.findClassroomById(Integer.valueOf(matcher1.group(2)));
                                if (classroom == null){
                                    formFlag = false;
                                    wrongMessage.put(line,i+"课程安排上课地点教室不存在！");
                                    break;
                                }
                                building = classroom.getBuilding();
                            }
                            else {
                                formFlag = false;
                                wrongMessage.put(line,"课程安排上课地点格式不正确！");
                                break;
                            }

                            classArrangement.setClassroom(classroom);
                            classArrangement.setDayOfWeek(dayOfWeek);
                            classArrangement.setBuilding(building);
                            classArrangement.setClassTimes(classTimes);
                            classArrangements.add(classArrangement);
                        }
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
            }
            reader.close();
            return wrongMessage;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
