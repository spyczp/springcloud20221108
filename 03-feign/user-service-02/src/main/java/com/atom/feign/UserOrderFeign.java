package com.atom.feign;

import com.atom.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @FeignClient(value = "order-service")
 * value：调用哪个应用的服务，就写哪个应用的名称
 */
@FeignClient(value = "order-service")
public interface UserOrderFeign {

    /**
     * 需要调用哪个controller，就把它的方法签名拷贝过来。
     * 下面的就是方法签名（包含一个方法的所有属性）
     * @return
     */
    @GetMapping("doOrder")
    public String doOrder();

    @GetMapping("onePath/{name}/and/{age}")
    public String onePath(@PathVariable("name") String name, @PathVariable("age") Integer age);

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = false, value = "name") String name);

    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false, value = "name") String name, @RequestParam(required = false, value = "age") Integer age);

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order);

    @PostMapping("oneObjAndOneParam")
    public String oneObjAndOneParam(@RequestBody Order order, @RequestParam("name") String name);

//    @GetMapping("testTime")
//    public String testTime(@RequestParam(required = false,value = "date") Date date);

//    @PostMapping("testTime")
//    public String testTime(@RequestBody Date date);

    @GetMapping("oneParam2")
    public String oneParam2(@RequestParam(required = false, value = "date") String date);

    @GetMapping("testDate")
    public String testDate(@RequestParam(required = false, value = "date") LocalDateTime date);

}
