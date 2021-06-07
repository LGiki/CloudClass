package com.fzu.CloudClass;


import com.fzu.CloudClass.Config.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@EnableCaching(proxyTargetClass = true)
@MapperScan({"com.fzu.CloudClass"})
@SpringBootApplication
public class Application {

    @Autowired
    private com.fzu.CloudClass.Config.Interceptor Interceptor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
