package com.atom.feign;

import com.atom.domain.Order;
import com.atom.feign.hystrix.UserOrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service", fallback = UserOrderFeignHystrix.class)
public interface UserOrderFeign {

    @GetMapping("/order/getOrderByUserId")
    public Order getOrderByUserId(@RequestParam(required = false) Integer userId);
}
