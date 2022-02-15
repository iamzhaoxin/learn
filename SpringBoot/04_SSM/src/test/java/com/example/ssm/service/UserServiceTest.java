package com.example.ssm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 16:31
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private IUserService iUserService;

    @Test
    void test(){
        System.out.println(userService.getAll());
    }

    @Test
    void testMP(){
        System.out.println(iUserService.list());
    }
}
