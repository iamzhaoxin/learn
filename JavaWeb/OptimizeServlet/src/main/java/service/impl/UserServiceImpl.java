package service.impl;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.User;
import service.UserService;
import util.SqlSessionFactoryUtils;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/1 23:01
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(id);
        sqlSession.close();
        return user;
    }
}
