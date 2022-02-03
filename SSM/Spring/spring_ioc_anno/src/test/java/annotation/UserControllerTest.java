package annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 22:49
 */
public class UserControllerTest {
    @Test
    public void controller() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.serviceSave();
    }
}
