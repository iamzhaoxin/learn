package com.example.active_record.testActiviRecord;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.active_record.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/10 22:34
 */
@SpringBootTest
public class ActiveRecordTests {

    @Test
    void testSelectById() {
        User user = new User();
        user.setId(7);

        // fixme 虽然没有显式使用UserMapper(Dao层，继承BaseMapper)，但底层还是用的BaseMapper，所以Dao层还是要写的
        User findUser = user.selectById();
        System.out.println(findUser);
    }

    @Test
    void insertUser(){
        User user = new User(null,"1234","password");
        boolean flag = user.insert();
        System.out.println(flag);
    }

    @Test
    void updateUser(){
        User user = new User(25,"-600","password");
        boolean flag = user.updateById();
        System.out.println(flag);
    }

    @Test
    void deleteUser(){
        User user=new User();
        user.setId(23);
        System.out.println(user.deleteById());
    }

    @Test
    void selectByCondition(){
        User user=new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("money","10000");   //筛选money大于等于10000的用户
        List<User> userList =user.selectList(queryWrapper);
        for(User _user : userList){
            System.out.println(_user);
        }
    }

}
