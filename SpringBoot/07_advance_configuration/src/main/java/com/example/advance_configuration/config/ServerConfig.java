package com.example.advance_configuration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/16 10:14
 */

@Component
@Data
@ConfigurationProperties(prefix = "servers")    //notice 自定义Bean的属性绑定
public class ServerConfig {
    private String port;
    private String ip;
    private String time;
}

/*
    notice @ConfigurationProperties是松散绑定
        - 属性IPAddress可以绑定到：
            - ipaddress
            - ipAddress(常用模式)
            - ip_address
            - ip-address(推荐模式)
        - prefix前缀名 的 命名规范：只能使用 纯小写、数字、中划线作为合法字符
        - 注：@Value不支持
 */
