package com.example.operation_maintain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.operation_maintain.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/12 20:08
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
