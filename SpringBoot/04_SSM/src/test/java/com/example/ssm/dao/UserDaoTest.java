package com.example.ssm.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ssm.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 15:24
 */
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void test1(){
        System.out.println(userDao.getById(1));
        System.out.println(userDao.selectById(2));
        System.out.println("-----------");

        System.out.println("分页查询");
        Page<User> page=userDao.selectPage(new Page<>(1, 5),null);
        System.out.println(page.getTotal());

        Page<User> page2=new Page<>(2,4);
        userDao.selectPage(page2,null);
        System.out.println(page2.getPages());

        System.out.println("条件查询");
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("password","123");
        System.out.println(userDao.selectList(queryWrapper));

        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.like(User::getMoney,"6");
        System.out.println(userDao.selectList(lqw));
        lqw.clear();
        Integer num=5;
        lqw.like(num!=null,User::getMoney,num);
        System.out.println(userDao.selectList(lqw));

    }

}
