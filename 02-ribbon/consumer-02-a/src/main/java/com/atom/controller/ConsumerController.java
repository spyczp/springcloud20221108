package com.atom.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("testRibbon")
    public String testRibbon(String serverName){
        String result = restTemplate.getForObject("http://" + serverName + "/hello", String.class);
        return result;
    }


    @GetMapping("testRibbonRule")
    public String getRibbonRule(String serverName){
        ServiceInstance choose = loadBalancerClient.choose(serverName);
        return choose.toString();
    }
}
