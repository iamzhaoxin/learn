package service;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.User;
import util.SqlSessionFactoryUtils;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/29 16:45
 */
public class GetUserById {
    public static User getUserById(int id) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.selectById(id);
        sqlSession.close();
        return user;
    }
}
