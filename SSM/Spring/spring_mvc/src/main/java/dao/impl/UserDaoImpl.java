package dao.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/5 09:09
 */


@Repository("userDao")
@Scope("prototype")
public class UserDaoImpl implements UserDao {
    @Value("${jdbc.username}")
    public String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void save() {
        System.out.println("username: " + username);
    }
}
