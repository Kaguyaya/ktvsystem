package com.kaguya.ktvadmin.config;

import com.kaguya.ktvadmin.filter.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class KtvInterceptors implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {

//    注册TestInterceptor拦截器
    InterceptorRegistration registration = registry.addInterceptor(new JWTInterceptor());
        registration.addPathPatterns("/user/list");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/user/login"            //登录
                );
}
}
