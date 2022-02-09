package dao;

import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:50
 */

public interface AccountDao {

    void out(int payer, double money);

    void in(int payee, double money);
}
