package com.ywz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class YwzRegisteryApplication {
    public static void main(String[] args) {
        SpringApplication.run(YwzRegisteryApplication.class);
    }
}
