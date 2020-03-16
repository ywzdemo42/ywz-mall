package com.ywz.cart.config;

import com.ywz.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 */
@Configuration
public class YwzWebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    //重写addInterceptor方法,引入登录拦截器，配置拦截路径 /** 拦截所有路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}
