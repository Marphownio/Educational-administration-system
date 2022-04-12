package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.*;
import com.example.lab.repository.StudentRepository;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.CommonService;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

// 用户的增删改查服务
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private SchoolService schoolService;

    @Resource
    private MajorService majorService;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage addUser(User user) {
        ResultMessage resultMessage;
        if (findUserByUserId(user.getUserId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if (user.getRole() == null || user.getRole() == UserRole.ADMIN || !commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor())) {
            resultMessage = ResultMessage.FAILED;
        } else {
            try {
                if (user.getRole() == UserRole.TEACHER) {
                    teacherRepository.save(new Teacher(user));
                    resultMessage = ResultMessage.SUCCESS;
                } else if (user.getRole() == UserRole.STUDENT) {
                    studentRepository.save(new Student(user));
                    resultMessage = ResultMessage.SUCCESS;
                } else {
                    resultMessage = ResultMessage.FAILED;
                }
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage BatchImportUser(MultipartFile file) {
        User user = new User();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            //首行列标题
            reader.readLine();
            while((line = reader.readLine())!= null){
                String []item = line.split(",");
                user.setUserId(Integer.valueOf(item[0]));
                user.setPassword(item[1]);
                user.setUsername(item[3]);
                user.setIdNumber(item[4]);
                user.setPhoneNumber(item[5]);
                user.setEmail(item[6]);
                user.setStatus(Boolean.valueOf(item[7]));
                user.setSchool(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])));
                user.setMajor(majorService.findMajorByMajorId(Integer.valueOf(item[9])));
                user.setRole(UserRole.valueOf(item[2]));
                addUser(user);
            }
            reader.close();
        } catch (IOException e) {
                return ResultMessage.FAILED;
        }finally {
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage deleteUser(Integer userId) {
        ResultMessage resultMessage;
        if (findUserByUserId(userId) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else {
            try {
                if(findTeacherByTeacherId(userId) != null) {
                    teacherRepository.deleteById(userId);
                    resultMessage = ResultMessage.SUCCESS;
                } else if (findStudentByStudentId(userId) != null) {
                    studentRepository.deleteById(userId);
                    resultMessage = ResultMessage.SUCCESS;
                } else {
                    resultMessage = ResultMessage.FAILED;
                }
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage updateUser(User user) {
        ResultMessage resultMessage;
        if (findUserByUserId(user.getUserId()) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else {
            if (!commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor())) {
                resultMessage = ResultMessage.FAILED;
            } else {
                try {
                    if (user.getRole() == UserRole.TEACHER) {
                        teacherRepository.save(new Teacher(user));
                        resultMessage = ResultMessage.SUCCESS;
                    } else if (user.getRole() == UserRole.STUDENT) {
                        studentRepository.save(new Student(user));
                        resultMessage = ResultMessage.SUCCESS;
                    } else {
                        resultMessage = ResultMessage.FAILED;
                    }
                } catch (Exception exception) {
                    resultMessage = ResultMessage.FAILED;
                }
            }
        }
        return resultMessage;
    }

    // 查询全部用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    // 查询全部教师
    @Override
    public List<Teacher> findAllTeacher() {
        return teacherRepository.findAll();
    }

    // 查询全部学生
    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    // 通过id查询用户
    @Override
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // 通过姓名查询用户
    @Override
    public List<User> findUserByUserName(String username) {
        return userRepository.findAllByUsername(username);
    }

    // 通过id查询教师
    @Override
    public Teacher findTeacherByTeacherId(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    // 通过id查询学生
    @Override
    public Student findStudentByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
