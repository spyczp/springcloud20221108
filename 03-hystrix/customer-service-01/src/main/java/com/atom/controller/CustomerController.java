package com.atom.controller;

import com.atom.feign.CustomerRentFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CustomerController {

    @Resource
    private CustomerRentFeign customerRentFreign;

    @GetMapping("customerRent")
    public String customerRent(){
        System.out.println("客户去租车");

        String result = customerRentFreign.rent();
        System.out.println(result);

        return "ok";
    }
}
