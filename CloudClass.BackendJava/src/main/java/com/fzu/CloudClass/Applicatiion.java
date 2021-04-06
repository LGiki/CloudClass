package com.fzu.CloudClass;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.fzu.CloudClass"})
@SpringBootApplication
public class Applicatiion {
    public static void main(String[] args) {
        SpringApplication.run(Applicatiion.class,args);
    }
}
