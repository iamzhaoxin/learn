package com.example.mybasemapper.config;

import com.example.mybasemapper.injectors.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 21:15
 */
@Configuration
public class MybatisConfig {

    //注入自定义SQL容器
    @Bean
    public MySqlInjector mysqlInjector(){
        return new MySqlInjector();
    }
}
