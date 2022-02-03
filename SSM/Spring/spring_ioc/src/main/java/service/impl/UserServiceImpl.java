package service.impl;

import dao.UserDao;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 9:56
 */
public class UserServiceImpl implements UserService {
    private UserDao user1;

    public void setUser1(UserDao user1) {
        this.user1 = user1;
    }

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao user1) {
        this.user1 = user1;
    }

    @Override
    public void serviceSave() {
/*硬编码，无依赖注入
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao user = (UserDao) app.getBean("userDao");
        user.save();
*/
        user1.save();
    }


}
