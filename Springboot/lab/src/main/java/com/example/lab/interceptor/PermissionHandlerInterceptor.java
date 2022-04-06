package com.example.lab.interceptor;

import com.example.lab.pojo.User;
import com.sun.istack.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 权限拦截器
public class PermissionHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        User currentUser = (User)request.getSession().getAttribute("user");
        // 仅管理员有权限添加用户
        if(currentUser != null && currentUser.getRole() != 0) {
            request.setAttribute("msg", "没有权限");
            request.getRequestDispatcher("/userManage.html").forward(request, response);
            return false;
        }
        else {
            return true;
        }
    }
}
