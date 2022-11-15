package com.atom.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;


@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        RequestPath path = request.getPath();
        System.out.println(path);
        String hostName = request.getRemoteAddress().getHostName();
        System.out.println(hostName);
        HttpHeaders headers = request.getHeaders();
        System.out.println(headers);
        String name = request.getMethod().name();
        System.out.println(name);

//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().set("content-type", "application/json;charset=utf-8");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("code", HttpStatus.UNAUTHORIZED.value());
//        map.put("msg", "你未授权");
//        ObjectMapper om = new ObjectMapper();
//        byte[] bytes = new byte[0];
//        try {
//            bytes = om.writeValueAsBytes(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        DataBuffer wrap = response.bufferFactory().wrap(bytes);
//
//        return response.writeWith(Mono.just(wrap));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
