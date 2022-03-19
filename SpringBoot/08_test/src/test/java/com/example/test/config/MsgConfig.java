package com.example.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/19 22:36
 */
@Configuration
public class MsgConfig {
    @Bean
    public String msg(){
        return "测试环境使用自定义bean";
    }
}
