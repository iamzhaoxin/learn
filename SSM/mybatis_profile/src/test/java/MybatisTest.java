import dao.test_tableDao;
import domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Properties;

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
        test_tableDao test_tableDao=sqlSession.getMapper(test_tableDao.class);
        List<User> users = test_tableDao.selectAll();
        System.out.println(users);
        sqlSession.close();
    }

}
