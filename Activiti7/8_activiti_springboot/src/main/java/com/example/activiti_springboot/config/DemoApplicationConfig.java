package com.example.activiti_springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/5 10:07
 */
@Slf4j
@Configuration
public class DemoApplicationConfig {

    /**
     * 添加Security用户
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //把用户存储在内存中
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //构造用户信息，角色前要加上ROLE_，组名称前面加GROUP_
        String[][] usersGroupAndRoles = {
                {"jack", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTEAM"},
                {"rose", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTEAM"},
                {"other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTEAM"},
                {"system", "password", "ROLE_ACTIVITI_USER"},
                {"admin", "password", "ROLE_ACTIVITI_ADMIN"},
        };
        for (String[] users : usersGroupAndRoles) {
            //用户的角色和组
            List<String> authStrList = Arrays.asList(Arrays.copyOfRange(users, 2, users.length));
            log.info("> Registering new user: {} with the following Authorities[{}]", users[0], authStrList);
            inMemoryUserDetailsManager.createUser(new User(
                    users[0],
                    passwordEncoder().encode(users[1]),
//                    authStrList.stream().map(str -> new SimpleGrantedAuthority(str)).collect(Collectors.toList())
                    authStrList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            ));
        }
        return inMemoryUserDetailsManager;
    }

    /**
     * 密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
