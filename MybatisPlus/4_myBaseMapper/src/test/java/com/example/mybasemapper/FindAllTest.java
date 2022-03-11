package com.example.mybasemapper;

import com.example.mybasemapper.dao.UserDao;
import com.example.mybasemapper.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 21:17
 */

@SpringBootTest
public class FindAllTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findAllTest(){
        List<User> users = this.userDao.findAll();
        for(User user:users){
            System.out.println(user);
        }
    }
}
