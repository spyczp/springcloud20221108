package com.atom;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class Consumer02AApplication {

    public static void main(String[] args) {
        SpringApplication.run(Consumer02AApplication.class, args);
    }

    @Bean
    @LoadBalanced //把restTemplate对象交给ribbon来调用
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 通过这种方式来指定全局的rule（我认为这里其实是更改了默认使用的规则）
     * @return
     */
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
