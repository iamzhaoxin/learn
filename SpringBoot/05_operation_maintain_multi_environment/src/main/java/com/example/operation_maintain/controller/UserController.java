package com.example.operation_maintain.controller;

import com.example.operation_maintain.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/12 18:02
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> getAll(){
        User user = new User();
        log.warn("accessing /users by GET!");
        return user.selectAll();
    }
}
