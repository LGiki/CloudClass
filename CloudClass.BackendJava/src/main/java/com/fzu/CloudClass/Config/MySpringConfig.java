package com.fzu.CloudClass.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@SpringBootConfiguration
public class MySpringConfig extends WebMvcConfigurerAdapter {

    @Bean
    Interceptor Interceptor(){
        return new Interceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //拦截全部 跨域   需要放在最上面
        registry.addInterceptor(Interceptor()).addPathPatterns("/**");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();

    }

}