package com.atom.feign.hystrix;

import com.atom.domain.Order;
import com.atom.feign.UserOrderFeign;
import org.springframework.stereotype.Component;

@Component
public class UserOrderFeignHystrix implements UserOrderFeign {
    @Override
    public Order getOrderByUserId(Integer userId) {

        Order order = Order.builder()
                .id(2)
                .name("备胎订单")
                .price(99D)
                .build();

        return order;
    }
}
