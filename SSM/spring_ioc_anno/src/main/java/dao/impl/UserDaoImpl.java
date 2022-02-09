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
 * @Date: 2022/2/2 23:09
 */

//<bean id="userDao" class="dao.impl.UserDaoImpl"/>
//@Component("userDao")
@Repository("userDao")
@Scope("prototype")
public class UserDaoImpl implements UserDao {
    @Value("${jdbc.username}")  //从容器加载了的配置文件中匹配key
    private String username;
    private List<String> strList;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    /*注解:初始化方法*/
    @PostConstruct
    public void init() {
        System.out.println("init...");
    }

    /*注解:销毁方法*/
    @PreDestroy
    public void destroy() {
        System.out.println("destroy...");
    }

    @Override
    public void save() {
        System.out.println("strList: " + strList);
        System.out.println("username: " + username);
    }
}
