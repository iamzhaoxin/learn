package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/19 22:51
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String get(){
        return "hello";
    }
}
