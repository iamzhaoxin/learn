package service.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2月5日09:53:57
 */

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserDao user1;

    public UserServiceImpl(@Qualifier("userDao") UserDao user1) {
        this.user1 = user1;
    }

    @Override
    public void serviceSave() {
        user1.save();
    }

}
