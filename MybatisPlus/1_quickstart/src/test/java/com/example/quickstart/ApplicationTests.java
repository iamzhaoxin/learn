package com.example.quickstart;

import com.example.quickstart.pojo.User;
import com.example.quickstart.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<User> users = userService.getAll();
        for(User user:users){
            System.out.println(user);
        }
    }

}
