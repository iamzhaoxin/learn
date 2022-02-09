package controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.AccountService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Controller("accountControllerSimulate")
@ContextConfiguration("classpath:application.xml")
public class AccountControllerSimulateTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void run() {
        this.accountService.transfer(1, 2, 500);
    }
}
