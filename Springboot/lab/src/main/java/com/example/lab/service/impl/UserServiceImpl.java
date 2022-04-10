package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.Teacher;
import com.example.lab.pojo.entity.User;
import com.example.lab.repository.StudentRepository;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.repository.UserRepository;
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

    @Override
    public ResultMessage BatchImportUser(MultipartFile file) {
        User user = new User();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));
            String line;
            while((line = reader.readLine())!= null){
                //csv文件是使用逗号作为分隔符的    但是如果密码中有逗号的话 就会导致错误  所以文件格式中 我们可以改变一下
                //比如将分隔符换成在用户信息中不可能出现的字符
                String []item = line.split(",");
                System.out.println(item.length);
                user.setUserId(new Integer(item[0]));
                user.setPassword(item[1]);
                user.setUsername(item[3]);
                user.setIdNumber(item[4]);
                user.setPhoneNumber(item[5]);
                user.setEmail(item[6]);
                //没有七是因为在添加的User肯定都是在校的状态 并且在实体类中以及赋了初始值 就可以不用再设定
                user.setSchool(schoolService.findSchoolBySchoolName(item[8]));
                user.setMajor(majorService.findMajorByMajorName(item[9]));
                if (item[2].equals("TEACHER")){
                    user.setRole(UserRole.TEACHER);
                    teacherRepository.save(new Teacher(user));
                }
                else {
                    user.setRole(UserRole.STUDENT);
                    studentRepository.save(new Student(user));
                }
            }
//
        } catch (IOException e) {
//            e.printStackTrace();
                return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
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
    }

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
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
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
