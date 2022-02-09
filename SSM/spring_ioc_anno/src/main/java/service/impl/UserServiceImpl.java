package service.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 9:56
 */

//<bean id="userService" class="service.impl.UserServiceImpl"/>
//@Component("userService")
@Service("userService")
public class UserServiceImpl implements UserService {
    //<property name="user1" ref="userDao"/>
//    @Autowired    //自动按照数据类型从Spring容器中匹配注入
//    @Qualifier("userDao") //根据id值注入,必须结合Autowired使用
//    @Resource(name = "userDao") //相当于@Autowired+@Qualifier
    private final UserDao user1;

    public UserServiceImpl(@Qualifier("userDao") UserDao user1) {
        this.user1 = user1;
    }

    @Override
    public void serviceSave() {
        user1.save();
    }


}
