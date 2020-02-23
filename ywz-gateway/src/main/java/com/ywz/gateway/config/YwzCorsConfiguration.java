package com.ywz.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class YwzCorsConfiguration {

    //注入跨域过滤器
    @Bean
    public CorsFilter corsFilter(){
        //返回corsFilter实例 参数：cors配置源对象

        //初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许跨域的域名 如果携带cookie则不能写*
        corsConfiguration.addAllowedOrigin("http://manage.ywz.com");
        corsConfiguration.addAllowedOrigin("http://www.ywz.com");
        //是否允许携带cookie
        corsConfiguration.setAllowCredentials(true);
        //头信息 允许任何头信息
        corsConfiguration.addAllowedHeader("*");
        //*代表所有的请求方法 get post put delete
        corsConfiguration.addAllowedMethod("*");

        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(corsConfigurationSource);
    }

}
