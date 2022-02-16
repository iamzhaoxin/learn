package com.example.ssm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ssm.dao.UserDao;
import com.example.ssm.domain.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 16:42
 */
@Service
public class IUserService extends ServiceImpl<UserDao, User>{

    private final UserDao userDao;

    public IUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public IPage<User> getPage(int currentPage, int pageSize, User user){
        IPage<User> userPage=new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(user.getId()),User::getId,user.getId());
        lqw.like(Strings.isNotEmpty(user.getMoney()),User::getMoney,user.getMoney());
        lqw.like(Strings.isNotEmpty(user.getPassword()),User::getPassword,user.getPassword());
        return userDao.selectPage(userPage,lqw);
    }
}
