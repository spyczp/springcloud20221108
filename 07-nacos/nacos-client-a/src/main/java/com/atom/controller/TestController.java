package com.atom.controller;

import com.atom.feign.ACallBFeign;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private ACallBFeign aCallBFeign;

    @GetMapping("test")
    public String test(){
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-client-car");
        System.out.println(instances);
        return "ok";
    }

    @GetMapping("callB")
    public String callB(){
        String info = aCallBFeign.info();
        return info;
    }
}
