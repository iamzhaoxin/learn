package com.example.activiti_springboot.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/5 9:48
 */

/*
    notice 添加SpringSecurity安全框架整合
    因为activiti7的springboot整合中，默认集成了SpringSecurity安全框架，要去配置SpringSecurity的用户权限配置信息
    SpringBoot的依赖包里有SpringSecurity
 */
@Component
public class SecurityUtil {
    private final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private final UserDetailsService userDetailsService;

    public SecurityUtil(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void logInAs(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user == null) {
            throw new IllegalStateException("User " + username + " doesn't exist. ");
        }
        logger.info("> Logged in as: {}",username);

        SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return user.getPassword();
            }

            @Override
            public Object getDetails() {
                return user;
            }

            @Override
            public Object getPrincipal() {
                return user;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        }));

        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
    }

}
