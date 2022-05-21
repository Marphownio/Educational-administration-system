package com.example.lab.service.impl;

import com.example.lab.pojo.enums.ResultMessage;
import com.example.lab.pojo.enums.UserRole;
import com.example.lab.pojo.entity.*;
import com.example.lab.repository.StudentRepository;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

// 用户的增删改查服务
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private SchoolService schoolService;

    @Resource
    private MajorService majorService;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage login(String userId, String password, HttpSession session) {
        ResultMessage resultMessage = ResultMessage.FAILED;
        if (userId.matches("^\\d{6}$") || userId.matches("^\\d{8}$")) {
            Admin admin = new Admin();
            admin.setUserId(0);
            admin.setPassword("fudan_admin");
            if (admin.getUserId().equals(parseInt(userId)) && password.equals(admin.getPassword())) {
                session.setAttribute("user", admin);
                return ResultMessage.SUCCESS_LOGIN_ADMIN;
            }
            User user = findUserByUserId(parseInt(userId));
            if (user != null && user.getPassword().equals(password)) {
                resultMessage = loginResult(user, session);
            }
        }
        return resultMessage;
    }
    @Override
    public ResultMessage loginResult(User user, HttpSession session) {
        ResultMessage resultMessage;
        switch (user.getRole()) {
            case TEACHER:
                if (Boolean.TRUE.equals(user.getStatus())) {
                    session.setAttribute("user", user);
                    resultMessage = user.getPassword().equals("fudan123456") ? ResultMessage.SUCCESS_LOGIN_TEACHER_RESETPASSWORD : ResultMessage.SUCCESS_LOGIN_TEACHER;
                } else {
                    resultMessage = ResultMessage.FAILED_DIMISSION;
                }
                break;
            case STUDENT:
                if (Boolean.TRUE.equals(user.getStatus())) {
                    session.setAttribute("user", user);
                    resultMessage = user.getPassword().equals("fudan123456") ? ResultMessage.SUCCESS_LOGIN_STUDENT_RESETPASSWORD : ResultMessage.SUCCESS_LOGIN_STUDENT;
                } else {
                    resultMessage = ResultMessage.FAILED_LEFT;
                }
                break;
            default:
                resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage logout(HttpSession session) {
        try {
            session.invalidate();
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception) {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage resetPassword(String newPassword, HttpSession session) {
        User user = findUserByUserId(((User)session.getAttribute("user")).getUserId());
        if(newPassword.equals(user.getPassword())) {
            return ResultMessage.FAILED;
        }
        else {
            user.setPassword(newPassword);
            try {
                updateUser(user);
                ResultMessage resultMessage;
                switch (user.getRole()) {
                    case TEACHER:
                        resultMessage = ResultMessage.SUCCESS_LOGIN_TEACHER; break;
                    case STUDENT:
                        resultMessage = ResultMessage.SUCCESS_LOGIN_STUDENT; break;
                    default:
                        resultMessage = ResultMessage.FAILED; break;
                }
                return resultMessage;
            }
            catch (Exception exception) {
                return ResultMessage.FAILED;
            }
        }
    }
    @Override
    public ResultMessage saveUser(User user) {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        try {
            switch (user.getRole()) {
                case STUDENT:
                    studentRepository.save(new Student(user));
                    break;
                case TEACHER:
                    teacherRepository.save(new Teacher(user));
                    break;
                default:
                    resultMessage = ResultMessage.FAILED;
            }
        } catch (Exception exception) {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage addUser(User user) {
        if (findUserByUserId(user.getUserId()) != null) {
            return ResultMessage.EXIST;
        }
        if (user.getRole() == null || user.getRole() == UserRole.ADMIN
                || Boolean.TRUE.equals(!commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor()))) {
            return ResultMessage.FAILED;
        }
        user.setPassword("fudan123456");
        return saveUser(user);
    }

    @Override
    public HashMap<String,String> batchImportUser(MultipartFile file) {
        User user = new User();
        HashMap<String,String> wrongMessage = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "GBK"))) {
            String line;
            line = reader.readLine();
            while((line = reader.readLine()) != null){

                String []item = line.split(",");
                boolean flag = false;
                for(String i : item){
                    if (i.length() == 0){
                        flag = true;
                        break;
                    }
                }
                if (item.length < 10 || flag){
                    wrongMessage.put(line,"必填项缺失");
                    continue;
                }
                boolean statuscheck;
                boolean match;
                if(!item[2].equals("TEACHER") && !item[2].equals("STUDENT")){
                    wrongMessage.put(line,"角色输入不正确");
                    continue;
                }
                if(UserRole.valueOf(item[2]) == UserRole.TEACHER && !item[0].matches("^[0-9]{8}$"))
                {
                    wrongMessage.put(line,"工号需为8位纯数字");
                    continue;
                }
                if(UserRole.valueOf(item[2]) == UserRole.STUDENT && !item[0].matches("^[0-9]{6}$")){
                    wrongMessage.put(line,"学号需为6位纯数字");
                    continue;
                }
                if(!item[4].matches("^[0-9]{18}$")){
                    wrongMessage.put(line,"身份证号需为18位纯数字");
                    continue;
                }
                if(!item[5].matches("^[0-9]{0,11}$"))
                {
                    wrongMessage.put(line,"手机号需为1开头的11位纯数字");
                    continue;
                }
                if(!item[3].matches("^[a-zA-Z\u4e00-\u9fa5]+$"))
                {
                    wrongMessage.put(line,"姓名输入只能为中英文");
                    continue;
                }
                statuscheck = item[7].equals("TRUE") || item[7].equals("FALSE");
                if(!statuscheck)
                {
                    wrongMessage.put(line,"状态输入不正确");
                    continue;
                }
                match = Boolean.TRUE.equals(commonService.isMatchSchoolAndMajor(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])),majorService.findMajorByMajorId(Integer.valueOf(item[9]))));
                if(!match)
                {
                    wrongMessage.put(line,"学院与专业不存在或不匹配");
                    continue;
                }
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
            return wrongMessage;
        }
        catch (Exception e) {
            return wrongMessage;
        }
    }

    @Override
    public ResultMessage deleteUser(Integer userId) {
        ResultMessage resultMessage;
        if (findUserByUserId(userId) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else {
            try {
                if(teacherRepository.findById(userId).isPresent()) {
                    teacherRepository.deleteById(userId);
                    resultMessage = ResultMessage.SUCCESS;
                } else if (studentRepository.findById(userId).isPresent()) {
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
        if (findUserByUserId(user.getUserId()) == null) {
            return ResultMessage.NOTFOUND;
        }
        if (Boolean.FALSE.equals(commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor()))) {
            return ResultMessage.FAILED;
        }
        return saveUser(user);
    }

    // 查询全部用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
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

}
