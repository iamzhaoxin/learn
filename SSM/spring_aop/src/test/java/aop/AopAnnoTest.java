package aop;

import controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 10:23
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-anno.xml")
public class AopAnnoTest {

    @Autowired
    private UserController userController;

    @Test
    public void test() {
        userController.save();
    }
}
