import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import domain.Order;
import domain.User;
import mapper.orderMapper;
import mapper.test_tableMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 22:53
 */
public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        try {
            // 1. 加载mybatis配置文件，获取SQLSessionFactory
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        orderMapper orderMapper=sqlSession.getMapper(mapper.orderMapper.class);

        //设置分页相关参数 当前页/每页显示的条数
        PageHelper.startPage(1,10);

        List<Order> orders = orderMapper.findAll();

        for (Order order:orders){
            System.out.println(order);
        }

        sqlSession.close();
    }

}
