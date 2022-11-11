package com.atom.controller;

import com.atom.domain.Order;
import com.atom.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements UserOrderFeign {
    @Override
    public Order getOrderByUserId(Integer userId) {

        System.out.println(userId);

        Order order = Order.builder()
                .name("白切鸡饭")
                .id(1)
                .price(21D)
                .build();

        return order;
    }
}
