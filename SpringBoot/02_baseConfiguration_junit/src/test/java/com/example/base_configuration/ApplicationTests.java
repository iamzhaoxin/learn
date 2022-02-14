package com.example.base_configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*notice 如果测试类的包路径和引导类的包路径不同,需要classes参数指定引导类的class文件*/
@SpringBootTest(classes = Application.class)
class ApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("running");
    }

}