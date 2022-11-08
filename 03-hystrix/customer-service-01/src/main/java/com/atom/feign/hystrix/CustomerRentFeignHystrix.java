package com.atom.feign.hystrix;

import com.atom.feign.CustomerRentFeign;
import org.springframework.stereotype.Component;

@Component
public class CustomerRentFeignHystrix implements CustomerRentFeign {
    /**
     * 这个方法就是备选方案
     * @return
     */
    @Override
    public String rent() {
        return "我是备胎";
    }
}
