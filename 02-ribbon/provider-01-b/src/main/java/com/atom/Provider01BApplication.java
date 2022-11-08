package com.atom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Provider01BApplication {

    public static void main(String[] args) {
        SpringApplication.run(Provider01BApplication.class, args);
    }

}
