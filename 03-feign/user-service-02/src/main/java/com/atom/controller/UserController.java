package com.atom.controller;

import com.atom.domain.Order;
import com.atom.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class UserController {

    @Resource
    private UserOrderFeign userOrderFeign;

    @GetMapping("/userDoOrder")
    public String userDoOrder(){

        String result = userOrderFeign.doOrder();

        return result;
    }

    @GetMapping("testParam")
    public String testParam(){
        String one = userOrderFeign.onePath("张三", 29);
        System.out.println(one);

        String two = userOrderFeign.oneParam("李四");
        System.out.println(two);

        String three = userOrderFeign.twoParam("王五", 40);
        System.out.println(three);

        Order order = Order.builder()
                        .name("牛排")
                        .price(199D)
                        .count(1)
                        .address("上海")
                        .build();

        String four = userOrderFeign.oneObj(order);
        System.out.println(four);

        String five = userOrderFeign.oneObjAndOneParam(order, "赵六");
        System.out.println(five);

        return "ok";
    }

    /*@GetMapping("time")
    public String time(){
        Date date = new Date();
        System.out.println(date);
        String result = userOrderFeign.testTime(date);
        return result;
    }*/

    /**
     * Sun Mar 20 10:24:13 CST 2022
     * Mon Mar 21 00:24:13 CST 2022  +- 14个小时
     * 1.不建议单独传递时间参数
     * 2.转成字符串   2022-03-20 10:25:55:213 因为字符串不会改变
     * 3.jdk LocalDate 年月日    LocalDateTime 会丢失s
     * 4.改feign的源码
     *
     * @return
     */
    @GetMapping("time")
    public String time(){
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
        String now = sdf.format(date);
        System.out.println(now);

        String result = userOrderFeign.oneParam2(now);


//        LocalDate now = LocalDate.now();
//        LocalDateTime now1 = LocalDateTime.now();
        return result;
    }

    @GetMapping("date")
    public String date(){
        LocalDateTime date = LocalDateTime.now();
        System.out.println(date);
        String result = userOrderFeign.testDate(date);
        return result;
    }
}
