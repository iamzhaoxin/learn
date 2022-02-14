package com.example.mybatis.domain;

import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/14 23:22
 */
@Component
public class User {
    private String id;
    private String money;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", money='" + money + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
