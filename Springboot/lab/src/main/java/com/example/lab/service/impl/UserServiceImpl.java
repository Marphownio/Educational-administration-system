package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.Teacher;
import com.example.lab.pojo.entity.User;
import com.example.lab.repository.StudentRepository;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private StudentRepository studentRepository;

    @Override
    public ResultMessage addUser(User user) {
        if (findUserByUserId(user.getUserId()) != null) {
            return ResultMessage.EXIST;
        }
        else {
            try {
                if (user.getRole() == UserRole.TEACHER) {
                    teacherRepository.save(new Teacher(user));
                    return ResultMessage.SUCCESS;
                } else if (user.getRole() == UserRole.STUDENT) {
                    studentRepository.save(new Student(user));
                    return ResultMessage.SUCCESS;
                } else {
                    return ResultMessage.FAILED;
                }
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

//    @Resource
//    private ReadExcel readExcel;
//
//    @Override
//    public boolean BatchImportUser(String name, MultipartFile file) {
//        boolean b = false;
//        //创建处理EXCEL
//        ReadExcel readExcel=new ReadExcel();
//        //解析excel，获取客户信息集合。
//        List<User> customerList = readExcel.getExcelInfo(name ,file);
//
//        if(customerList != null){
//            b = true;
//        }
//
//        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
//        for(Customer customer:customerList){
//            customerDoImpl.addCustomers(customer);
//        }
//        return b;
//    }

    @Override
    public ResultMessage deleteUser(Integer userId) {
        if (findUserByUserId(userId) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                if(findTeacherByTeacherId(userId) != null) {
                    teacherRepository.deleteById(userId);
                    return ResultMessage.SUCCESS;
                } else if (findStudentByStudentId(userId) != null) {
                    studentRepository.deleteById(userId);
                    return ResultMessage.SUCCESS;
                }
                return ResultMessage.FAILED;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public ResultMessage updateUser(User user) {
        if (findUserByUserId(user.getUserId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        else {
            try {
                if (user.getRole() == UserRole.TEACHER) {
                    teacherRepository.save(new Teacher(user));
                    return ResultMessage.SUCCESS;
                } else if (user.getRole() == UserRole.STUDENT) {
                    studentRepository.save(new Student(user));
                    return ResultMessage.SUCCESS;
                } else {
                    return ResultMessage.FAILED;
                }
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Teacher findTeacherByTeacherId(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    @Override
    public Student findStudentByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
