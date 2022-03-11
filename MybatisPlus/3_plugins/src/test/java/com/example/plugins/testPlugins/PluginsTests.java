package com.example.plugins.testPlugins;

import com.example.plugins.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 20:09
 */
@SpringBootTest
public class PluginsTests {

    @Test
    void updatePluginTest(){
        User user = new User(1,null,"333",null);
        user.updateById();
    }


}
