package com.example.ssm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssm.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 15:22
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from test.test_table where id=#{id}")
    User getById(int id);
}
