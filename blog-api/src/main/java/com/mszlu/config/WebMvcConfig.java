package com.mszlu.config;

import com.mszlu.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，前端是8080，这里定义的接口是8888，所以要跨域
        //所有的都允许8080端口访问
        registry.addMapping("/**").allowedOrigins("http://localhost:8080/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //测试用的路径
                .addPathPatterns("/test")
                //登录才能评论
                .addPathPatterns("/comments/create/change")
                //登录才能发文章
                .addPathPatterns("/articles/publish");
        

    }
}
