package com.example.quickstart.service;

import com.example.quickstart.dao.UserDao;
import com.example.quickstart.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/16 21:37
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll(){
        return userDao.selectList(null);
    }
}
