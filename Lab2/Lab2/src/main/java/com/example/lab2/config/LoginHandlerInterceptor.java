package com.example.lab2.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果登录成功，则会有用户的session
        Object user = request.getSession().getAttribute("user");
        if(user == null) {
            //未登录，返回登录页面
            request.setAttribute("msg", "没有权限,请先登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }
        else {
            return true;
        }
    }
}