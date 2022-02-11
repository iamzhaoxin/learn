import domain.Order;
import domain.User;
import mapper.orderMapper;
import mapper.test_tableMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 22:53
 */
public class MybatisTest {

    private static SqlSession sqlSession;
    private static test_tableMapper userMapper;
    private static orderMapper orderMapper;

    @BeforeAll
    static void before() {
        try {
            System.out.println("开始初始化数据库连接池");
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(test_tableMapper.class);
            orderMapper = sqlSession.getMapper(orderMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws Exception {
        List<Order> orders = orderMapper.AnnoFindAll2();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void test2() {
        List<User> users = userMapper.AnnoFindAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test3() {
        List<Order> orders = orderMapper.findByUid(1);
        System.out.println(orders);
    }

    @AfterAll
    static void after() {
        sqlSession.close();
        System.out.println("closed sqlSession!");
    }

}
