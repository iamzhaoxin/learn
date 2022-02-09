package service.impl;

import dao.AccountDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.AccountService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:50
 */
@Service("accountService")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)    //配置当前类所有方法的事务控制参数
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)    //配置当前方法的事务控制参数,优先级更高
    public void transfer(int payer, int payee, double money) {
        accountDao.out(payer, money);
        int error = 1 / 0;
        accountDao.in(payee, money);
    }
}
