package demo;

import dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/2 23:15
 */
public class UserDaoDemo {
    public static void main(String[] args) {
        ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao user= (UserDao) app.getBean("userDao");
        user.save();
    }
}
