package com.ywz.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ywz.user.mapper")
public class YwzUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(YwzUserServiceApplication.class);
    }
}
