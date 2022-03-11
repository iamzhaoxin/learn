package com.example.plugins.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.example.plugins.plugins.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 20:10
 */
@Configuration
public class MybatisConfig {

    /*
        notice 注入自定义拦截器（插件）的方法
            - 通过mybatis-config.xml配置
                <configuration>
                    <plugins>
                        <plugin interceptor="com.example.plugins.plugins.MyInterceptor"/>
            - 如下 ↓
     */
    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // notice 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
