package com.example.quickstart.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/8 10:37
 */

@Configuration
public class MybatisPlusConfig {

    @Bean   //新版本的MP分页插件
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor page = new MybatisPlusInterceptor();
        page.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // fixme 如果不加这行，getTotal()=0
        return page;
    }
}
