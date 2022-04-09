package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.Teacher;
import com.example.lab.pojo.entity.User;
import com.example.lab.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static com.example.lab.LabApplication.admin;
import static java.lang.Integer.parseInt;

@RestController
public class LoginController {

    @Resource
    public UserService userService;

    // 登录
    @PostMapping(value = "/login")
    public ResultMessage login(@RequestParam(value = "loginid") String userId, @RequestParam(value = "loginpw") String password , HttpSession session) {

        if (userId.matches("^\\d{6}$") || userId.matches("^\\d{8}$")) {
            if (admin.getUserId().equals(parseInt(userId)) && password.equals(admin.getPassword())) {
                session.setAttribute("admin", admin);
                return ResultMessage.SUCCESS_LOGIN_ADMIN;
            } else {
                Teacher teacher = userService.findTeacherByTeacherId(parseInt(userId));
                Student student = userService.findStudentByStudentId(parseInt(userId));
                if(teacher != null && student != null) {
                    return ResultMessage.FAILED;
                }
                if (teacher != null && teacher.getPassword().equals(password) && teacher.getStatus()) {
                    session.setAttribute("user", teacher);
                    if (teacher.getPassword().equals("fudan123456")) {
                        return ResultMessage.SUCCESS_LOGIN_TEACHER_RESETPASSWORD;
                    } else {
                        return ResultMessage.SUCCESS_LOGIN_TEACHER;
                    }
                } else if (student != null && student.getPassword().equals(password) && student.getStatus()) {
                    session.setAttribute("user", student);
                    if (student.getPassword().equals("fudan123456")) {
                        return ResultMessage.SUCCESS_LOGIN_STUDENT_RESETPASSWORD;
                    } else {
                        return ResultMessage.SUCCESS_LOGIN_STUDENT;
                    }
                }
            }
        }
        return ResultMessage.FAILED;
    }
}
