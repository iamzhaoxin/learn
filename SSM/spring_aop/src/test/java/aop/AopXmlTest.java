package aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.UserService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 10:23
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public class AopXmlTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        userService.save();
    }
}
