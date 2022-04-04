//package com.example.lab.config;
//
//import com.example.lab.interceptor.LoginHandlerInterceptor;
//import com.example.lab.interceptor.PermissionHandlerInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyMvcConfigurer implements WebMvcConfigurer {
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("login");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/login.html").setViewName("login");
//        registry.addViewController("/index").setViewName("index");
//        registry.addViewController("/index.html").setViewName("index");
//        registry.addViewController("/resetPassword").setViewName("resetPassword");
//        registry.addViewController("/resetPassword.html").setViewName("resetPassword");
//        registry.addViewController("/userManage").setViewName("userManage");
//        registry.addViewController("/userManage.html").setViewName("userManage");
//        registry.addViewController("/addUser").setViewName("addUser");
//        registry.addViewController("/addUser.html").setViewName("addUser");
//    }
//
//    // 开启拦截器的配置路径
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 登录拦截
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/login.html", "/css/**", "/image/**", "/js/**");
//
//        // 权限拦截
//        registry.addInterceptor(new PermissionHandlerInterceptor()).addPathPatterns("/addUser", "/addUser.html");
//    }
//}
