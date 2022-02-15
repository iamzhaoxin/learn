package com.example.ssm.config;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 15:51
 */
@Configuration
public class MPconfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        // 创建MP拦截器
        MybatisPlusInterceptor interceptor=new MybatisPlusInterceptor();
        // 添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
