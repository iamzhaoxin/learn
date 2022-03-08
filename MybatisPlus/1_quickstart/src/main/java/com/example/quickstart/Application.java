package com.example.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    notice 包扫描
        springboot默认会扫描启动类所在的包及其子包；
        如果想要在启动类所在包以外定义控制器的话需要在启动类重写
            @ComponentScan等类似注解
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
