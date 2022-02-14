package com.example.mybatis.dao;

import com.example.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/14 23:23
 */
@Mapper
public interface UserDao {
    @Select("select * from test.test_table where id=#{id}")
    User getById(int id);
}
