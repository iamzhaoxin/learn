package com.example.base_configuration.controller;

import com.example.base_configuration.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/14 19:22
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${read_yml_value}")
    private String yml_value;

    @Value("${users[0].age}")
    private Integer age;

    // notice 使用Environment封装yml所有的键值对,并用Autowired自动装配
    private final Environment environment;

    private final User user1;

    public UserController(User user1, Environment environment) {
        this.user1 = user1;
        this.environment = environment;
    }

    @GetMapping
    public String getSomething(){
        System.out.println(yml_value);
        System.out.println(age);
        System.out.println(environment.getProperty("users[1].name"));
        System.out.println(user1);
        return age.toString();
    }
}
