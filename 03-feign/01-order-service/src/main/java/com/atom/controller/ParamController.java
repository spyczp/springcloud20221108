package com.atom.controller;

import com.atom.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class ParamController {


    @GetMapping("onePath/{name}/and/{age}")
    public String onePath(@PathVariable("name") String name, @PathVariable("age") Integer age){
        System.out.println("onePath" + name + ": " + age);
        return "ok";
    }

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = false, value = "name") String name){
        System.out.println("oneParam" + name);
        return "ok";
    }

    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false, value = "name") String name, @RequestParam(required = false, value = "age") Integer age){
        System.out.println("twoParam" + name + ": " + age);
        return "ok";
    }

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order){
        System.out.println(order);
        return "ok";
    }

    @PostMapping("oneObjAndOneParam")
    public String oneObjAndOneParam(@RequestBody Order order, @RequestParam("name") String name){
        System.out.println(name);
        System.out.println(order);
        return "ok";
    }

    @GetMapping("oneParam2")
    public String oneParam2(@RequestParam(required = false, value = "date") String date){
        System.out.println("oneParam2" + date);
        return "ok";
    }

    @GetMapping("testDate")
    public String testDate(@RequestParam(required = false, value = "date") LocalDateTime date){
        System.out.println(date);
        return "ok";
    }
}
