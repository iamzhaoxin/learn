package service.impl;

import dao.AccountDao;
import org.springframework.stereotype.Service;
import service.AccountService;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:50
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(int payer, int payee, double money) {
        accountDao.out(payer, money);
        int error = 1 / 0;
        accountDao.in(payee, money);
    }
}
