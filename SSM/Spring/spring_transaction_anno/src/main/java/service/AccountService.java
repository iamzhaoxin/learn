package service;

import org.springframework.stereotype.Service;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:50
 */

public interface AccountService {
    public void transfer(int payer, int payee, double money);
}
