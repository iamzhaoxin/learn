package com.example.other;

import com.example.other.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        User user = new User(null,"222",null,null);
        user.insert();
    }

    @Test
    void deleteTest(){
        User user=new User();
        user.setId(11);
        user.deleteById();
    }

}
