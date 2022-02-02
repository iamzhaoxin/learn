package service;

import pojo.User;
import service.impl.UserServiceImpl;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/1 23:01
 */
public interface UserService {
    User getUserById(int id);
}
