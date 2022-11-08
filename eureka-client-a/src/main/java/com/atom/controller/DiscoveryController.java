package com.atom.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DiscoveryController {

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("test")
    @ResponseBody
    public String doDiscovery(String serviceName){

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

        instances.forEach(System.out::println);

        ServiceInstance serviceInstance = instances.get(0);
        System.out.println(serviceInstance.getHost());
        System.out.println(serviceInstance.getPort());

        return instances.get(0).toString();
    }
}
