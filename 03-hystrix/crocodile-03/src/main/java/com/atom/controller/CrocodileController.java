package com.atom.controller;

import com.atom.anno.Crocodile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CrocodileController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("doRpc")
    @Crocodile
    public String doRpc(){
        String result = restTemplate.getForObject("http://localhost:8989/abc", String.class);
        return result;
    }
}
