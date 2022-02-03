package factory;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 9:50
 */
public class DynamicFactory {
    public UserDao getUserDao() {
        System.out.println("实例方法实例化工厂");
        return new UserDaoImpl();
    }
}
