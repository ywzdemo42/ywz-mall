package com.ywz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ywz.item.mapper")
public class YwzItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(YwzItemApplication.class);
    }
}
