package com.atom.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacos-client-car")
public interface ACallBFeign {

    @GetMapping("info")
    String info();
}
