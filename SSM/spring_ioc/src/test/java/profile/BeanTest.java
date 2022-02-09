package profile;

import dao.UserDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 9:07
 */
public class BeanTest {
    /*从类的根路径下加载配置文件,推荐*/
    ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    /*从磁盘路径上加载配置文件*/
//    ApplicationContext app2=new FileSystemXmlApplicationContext("C:\\Users\\zhaox\\Documents\\IDEA Projects\\learn\\SSM\\Spring\\spring_ioc\\src\\main\\resources\\applicationContext.xml");
    /*使用注解配置容器对象时,用这种方法创建Spring容器*/
//    ApplicationContext app3=new AnnotationConfigApplicationContext("");

    @Test
    public void Bean() {
        UserDao user = (UserDao) app.getBean("userDao");
        UserDao user2 = (UserDao) app.getBean("userDao");
        //验证 单例||多例
        System.out.println(user == user2);
        // destroy
        app.close();
    }

    @Test
    public void FactoryInstantiation() {
        UserDao user = (UserDao) app.getBean("userDaoStaticFactory");
        user.save();
    }

    @Test
    public void FactoryDynamic() {
        UserDao user = (UserDao) app.getBean("userDaoDynamicFactory");
        user.save();
    }

    /**
     * 模拟control层，调用service
     */
    @Test
    public void Controller() {
        System.out.println("\n****************dependency injection");
        UserService userService = (UserService) app.getBean("userService");
        userService.serviceSave();
    }

    @Test
    public void Injection() {
        System.out.println("\n普通类型的注入");
        UserDao user = (UserDao) app.getBean("userDaoWithParams");
        user.show();
        System.out.println("集合类型的注入");
        UserDao user2 = (UserDao) app.getBean("userDaoWithSet");
        user2.showSet();
    }

}

