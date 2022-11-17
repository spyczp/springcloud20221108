package com.atom.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 自定义请求限制的
 */
@Configuration
public class RequestLimitConfig {


    /**
     * 针对某一ip来限流
     * @return
     */
    @Bean
    @Primary
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostString());
    }

    /**
     * 针对某一个路径来限制：/doLogin
     * @return
     */
    @Bean
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}
