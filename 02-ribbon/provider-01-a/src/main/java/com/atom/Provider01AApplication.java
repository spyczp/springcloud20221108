package com.atom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Provider01AApplication {

    public static void main(String[] args) {
        SpringApplication.run(Provider01AApplication.class, args);
    }

}
