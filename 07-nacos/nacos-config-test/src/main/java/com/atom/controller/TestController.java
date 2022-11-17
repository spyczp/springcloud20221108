package com.atom.controller;

import com.atom.domain.Hero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private Hero hero;

    @GetMapping("test")
    public String test(){
        return hero.getName() + "--" + hero.getAge() + "--" + hero.getSex() + "--" + hero.getHobby();
    }
}
