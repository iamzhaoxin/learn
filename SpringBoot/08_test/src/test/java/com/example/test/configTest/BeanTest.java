package com.example.test.configTest;

import com.example.test.Application;
import com.example.test.config.MsgConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/19 22:38
 */
@SpringBootTest(classes = Application.class)
@Import(MsgConfig.class)
public class BeanTest {

    @Autowired
    private String s;

    @Test
    void testConfiguration(){
        System.out.println(s);
    }
}
