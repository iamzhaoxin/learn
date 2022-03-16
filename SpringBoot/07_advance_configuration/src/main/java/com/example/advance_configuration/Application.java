package com.example.advance_configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.advance_configuration.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableConfigurationProperties(ServerConfig.class)    notice 这个注解也是将ServerConfig.class加入到Spring容器，会和ServerConfig类的@Componet冲突（报错重名bean），所以二选一使用
public class Application {

    @Bean
    @ConfigurationProperties(prefix = "datasource") //notice 第三方bean的属性绑定
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

        ServerConfig serverConfig=run.getBean(ServerConfig.class);
        System.out.println(serverConfig);

        DruidDataSource dataSource=run.getBean(DruidDataSource.class);
        System.out.println(dataSource.getUsername());

    }

}
