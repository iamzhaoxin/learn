package dao.impl;

import dao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:50
 */

@Component("account")
public class AccountDaoImpl implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void out(int payer, double money) {
        jdbcTemplate.update("update test.test_table set money=money-? where id=?", money, payer);
    }

    @Override
    public void in(int payee, double money) {
        jdbcTemplate.update("update test.test_table set money=money+? where id=?", money, payee);
    }
}
