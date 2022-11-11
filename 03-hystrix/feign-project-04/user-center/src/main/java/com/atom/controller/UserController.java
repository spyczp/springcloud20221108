package com.atom.controller;

import com.atom.domain.Order;
import com.atom.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
class UserController {

    @Resource
    private UserOrderFeign userOrderFeign;

    @GetMapping("findOrder")
    public Order findOrder(){
        return userOrderFeign.getOrderByUserId(1);
    }
}
