package com.example.lab2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/homepage").setViewName("homepage");
        registry.addViewController("/homepage.html").setViewName("homepage");
        registry.addViewController("/passwordreset").setViewName("passwordreset");
        registry.addViewController("/passwordreset.html").setViewName("passwordreset");
    }

    //开启拦截器的配置路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/login.html", "/css/**", "/img/**", "/homepage", "/homepage.html", "/passwordreset","/passwordreset.html");
    }
}
