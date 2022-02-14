package com.example.quickstart_aliyun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/14 16:25
 */
@RestController
@RequestMapping("/quickstart")
public class DemoController {

    @GetMapping
    public String test(){
        System.out.println("i am running");
        return "running";
    }
}
