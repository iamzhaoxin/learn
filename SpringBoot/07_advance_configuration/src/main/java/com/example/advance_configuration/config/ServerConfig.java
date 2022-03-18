package com.example.advance_configuration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/16 10:14
 */

@Component
@Data
@ConfigurationProperties(prefix = "servers")    //notice 2. 自定义Bean的属性绑定
@Validated  //notice 开启对bean属性注入的校验
public class ServerConfig {
    @Max(value = 8888,message = "最大值不能超过8888")  // notice 3. 设置检验规则（其他规则参考源码）
    private int port;
    private String ip;
    private String time;

    //notice SpringBoot支持JDK8提供的时间空间单位
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration timeOut;
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize dataSize;
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
