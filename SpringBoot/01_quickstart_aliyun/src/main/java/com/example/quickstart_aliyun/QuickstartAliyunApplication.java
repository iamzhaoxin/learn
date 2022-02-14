package com.example.quickstart_aliyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    notice 引导类 初始化Spring容器
        扫描引导类所在包,加载Bean
 */
@SpringBootApplication
public class QuickstartAliyunApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickstartAliyunApplication.class, args);
    }

}
