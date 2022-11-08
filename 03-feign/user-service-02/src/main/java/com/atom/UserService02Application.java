package com.atom;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.atom.feign") //开启feign客户端功能，才能帮我们发起远程调用
public class UserService02Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService02Application.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 设置打印feign日志信息的级别，FULL是打印所有日志
     * @return
     */
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
