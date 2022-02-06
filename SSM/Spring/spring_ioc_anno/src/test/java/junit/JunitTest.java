package junit;

import config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/5 9:10
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
//@ContextConfiguration(classes = {SpringConfiguration.class})
public class JunitTest {
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        System.out.println("用SpringJunit创建Spring容器并注入");
        userService.serviceSave();
    }
}
