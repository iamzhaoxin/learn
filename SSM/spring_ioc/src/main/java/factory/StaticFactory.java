package factory;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 9:34
 */
public class StaticFactory {
    /*工厂静态方法实例化*/
    public static UserDao getUserDao(){
        System.out.println("静态方法实例化工厂");
        return new UserDaoImpl();
    }
}
