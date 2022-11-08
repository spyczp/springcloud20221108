package com.atom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RentCar02Application {

    public static void main(String[] args) {
        SpringApplication.run(RentCar02Application.class, args);
    }

}
