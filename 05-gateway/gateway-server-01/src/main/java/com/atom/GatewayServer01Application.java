package com.atom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServer01Application {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServer01Application.class, args);
    }

}
