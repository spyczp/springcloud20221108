package com.atom.controller;

import com.atom.model.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.UUID;

@RestController
public class LoginController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("doLogin")
    public String doLogin(@RequestParam(required = false) String name, @RequestParam(required = false) String password){
        System.out.println(name);
        System.out.println(password);

        User user = new User();
        user.setId(1001);
        user.setName(name);
        user.setPassword(password);
        user.setAge(30);

        String token = UUID.randomUUID().toString();

        //用户数据保存到redis
        stringRedisTemplate.opsForValue().set(token, user.toString(), Duration.ofSeconds(7200));

        return token;
    }
}
