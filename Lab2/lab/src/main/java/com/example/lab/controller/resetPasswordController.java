package com.example.lab.controller;

import com.example.lab.pojo.User;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class resetPasswordController {
    @Resource
    public UserService userService;
    @GetMapping (value ="resetPassword")
    public String resetPassword(@RequestParam("newPassword1") String newPassword1, @RequestParam("newPassword2") String newPassword2, Model model, HttpSession session) {
        if (!newPassword1.equals(newPassword2)) {
            model.addAttribute("msg1", "请两次输入的密码保持一致");
            return "/resetPassword";
        }
        else if(newPassword1.equals("123456")) {
            model.addAttribute("msg1", "请输入与初始密码不同的新密码");
            return "/resetPassword";
        }
        else{
            @NotNull HttpServletRequest request;
//            HttpSession session = ServletActionContext.getRequest().getSession();
//            String userId = session.getAttribute("userId");
//            String name = request.getSession().getAttribute("userName").toString();
//            String id=(String)session.getAttribute("user");
//            User user= new User(id,newPassword1);
            userService.updateUser(user);
            return "redirect:/index";
        }
    }
}
