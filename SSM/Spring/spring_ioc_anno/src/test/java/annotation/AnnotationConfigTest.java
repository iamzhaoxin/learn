package annotation;

import config.SpringConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/4 17:38
 */
public class AnnotationConfigTest {
    @Test
    public void AnnotationTest() {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserService userService = (UserService) app.getBean("userService");
        userService.serviceSave();
    }
}
