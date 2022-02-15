package com.example.ssm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ssm.dao.UserDao;
import com.example.ssm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 16:24
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Boolean save(User user){
        return userDao.insert(user)>0;
    }

    public List<User> getAll(){
        return userDao.selectList(null);
    }

    Page<User> getPage(int currentPage,int pageSize){
        Page<User> userPage=new Page<>(currentPage,pageSize);
        return userDao.selectPage(userPage,null);
    }
}
