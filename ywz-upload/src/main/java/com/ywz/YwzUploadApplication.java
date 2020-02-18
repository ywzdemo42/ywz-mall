package com.ywz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YwzUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(YwzUploadApplication.class);
    }
}
