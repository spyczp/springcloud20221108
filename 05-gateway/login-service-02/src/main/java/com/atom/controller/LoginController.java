package com.atom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LoginController {

    @GetMapping("doLogin")
    public String doLogin(@RequestParam(required = false) String name, @RequestParam(required = false) String password){
        System.out.println(name);
        System.out.println(password);

        UUID token = UUID.randomUUID();

        return token + " 服务1";
    }
}
