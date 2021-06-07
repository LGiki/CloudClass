package com.fzu.CloudClass.Config;

import com.fzu.CloudClass.util.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JWTInterceptor()) //注册自定义拦截器
//                .addPathPatterns("/**") //拦截所有路径
//                .excludePathPatterns("/api/CloudClass/authentication/**")
//                .excludePathPatterns("/api/CloudClass/allPerson");
    }
}
