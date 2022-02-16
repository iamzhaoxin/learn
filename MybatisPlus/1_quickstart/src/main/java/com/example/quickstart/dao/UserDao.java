package com.example.quickstart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quickstart.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/16 21:33
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
