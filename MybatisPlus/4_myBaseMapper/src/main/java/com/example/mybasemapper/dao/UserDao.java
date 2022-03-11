package com.example.mybasemapper.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybasemapper.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/10 22:49
 */

@Mapper
public interface UserDao extends MyBaseMapper<User> {

}
