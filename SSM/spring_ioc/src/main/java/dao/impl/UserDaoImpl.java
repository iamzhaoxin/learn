package dao.impl;

import dao.UserDao;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/2 23:09
 */
public class UserDaoImpl implements UserDao {
    private String username;
    private int age;
    private List<String> strList;
    private Map<String, Integer> userMap;
    private Properties properties;

    public UserDaoImpl() {
        System.out.println("构造函数……");
    }

    public void init() {
        System.out.println("init...");
    }

    public void destroy() {
        System.out.println("destroy...");
    }

    public void save() {
        System.out.println("save running...");
    }

    public void show() {
        System.out.println(username + " === " + age);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setUserMap(Map<String, Integer> userMap) {
        this.userMap = userMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void showSet() {
        System.out.println(strList);
        System.out.println(userMap);
        System.out.println(properties);
    }
}
