package service;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.User;
import util.SqlSessionFactoryUtils;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/28 19:13
 */
public class LoginVerify {
    public enum LoginStatus {
        ValueIsNull, NotExist, ErrorPassword, Success
    }

    public LoginStatus login(Integer id, String password) {
        if (id == null || password == null) {
            return LoginStatus.ValueIsNull;
        }

        User user = GetUserById.getUserById(id);

        if (user == null) {
            return LoginStatus.NotExist;
        } else if (!user.getPassword().equals(password)) {               //notice 用 != 比较的是对象的内存地址而不是值
            return LoginStatus.ErrorPassword;
        } else {
            return LoginStatus.Success;
        }
    }
}
