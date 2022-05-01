package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.entity.Student;
import com.example.lab.pojo.entity.Teacher;
import com.example.lab.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.example.lab.LabApplication.admin;
import static java.lang.Integer.parseInt;

@RestController
public class LoginController {

    @Resource
    public UserService userService;

    // 登录
    @PostMapping(value = "/login")
    public ResultMessage login(@RequestParam(value = "loginid") String userId, @RequestParam(value = "loginpw") String password , HttpSession session) {

        ResultMessage resultMessage;
        if (userId.matches("^\\d{6}$") || userId.matches("^\\d{8}$")) {
            if (admin.getUserId().equals(parseInt(userId)) && password.equals(admin.getPassword())) {
                session.setAttribute("user", admin);
                resultMessage = ResultMessage.SUCCESS_LOGIN_ADMIN;
            }
            else {
                Teacher teacher = userService.findTeacherByTeacherId(parseInt(userId));
                Student student = userService.findStudentByStudentId(parseInt(userId));
                if (teacher != null && student != null) {
                    resultMessage = ResultMessage.FAILED;
                } else if (teacher != null && teacher.getPassword().equals(password)) {
                    if (teacher.getStatus()) {
                        session.setAttribute("user", teacher);
                        if (teacher.getPassword().equals("fudan123456")) {
                            resultMessage = ResultMessage.SUCCESS_LOGIN_TEACHER_RESETPASSWORD;
                        } else {
                            resultMessage = ResultMessage.SUCCESS_LOGIN_TEACHER;
                        }
                    } else {
                        resultMessage = ResultMessage.FAILED_DIMISSION;
                    }
                } else if (student != null && student.getPassword().equals(password)) {
                    if (student.getStatus()) {
                        session.setAttribute("user", student);
                        if (student.getPassword().equals("fudan123456")) {
                            resultMessage = ResultMessage.SUCCESS_LOGIN_STUDENT_RESETPASSWORD;
                        } else {
                            resultMessage = ResultMessage.SUCCESS_LOGIN_STUDENT;
                        }
                    } else {
                        resultMessage = ResultMessage.FAILED_LEFT;
                    }
                } else {
                    resultMessage = ResultMessage.FAILED;
                }
            }
        } else {
            resultMessage = ResultMessage.FAILED;
        }
        return resultMessage;
    }
}
