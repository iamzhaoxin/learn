package service.impl;

import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 21:22
 */
public class UserServiceImpl implements UserService {
    @Override
    public void save() {
        System.out.println("service is running save action");
    }
}
