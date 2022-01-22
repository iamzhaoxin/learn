import mapper.test_tableMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.User;

import java.io.InputStream;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/22 17:07
 */
public class MyBatisDemo {

    public static void main(String[] args) throws Exception {
        // 1. 加载mybatis配置文件，获取SQLSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2. 获取SqlSession对象，用于执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 执行sql并保存结果
        List<User> users = sqlSession.selectList("mapper.test_tableMapper.selectAll");
        // 3.1 获得test_tableMapper接口的代理对象
        test_tableMapper testTableMapper =sqlSession.getMapper(test_tableMapper.class);
        List<User> users1 = testTableMapper.selectAll();

        System.out.println(users);
        System.out.println(users1);

        // 4. 释放资源
        sqlSession.close();
    }
}
