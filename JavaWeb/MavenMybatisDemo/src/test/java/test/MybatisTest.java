package test;

import mapper.test_tableMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/23 15:44
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
    public void testSelectAll() {

        // 2. 获取SqlSession对象，用于执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 执行sql并保存结果
        List<User> users = sqlSession.selectList("mapper.test_tableMapper.selectAll");
        // 3.1 notice 获得test_tableMapper接口的代理对象
        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);
        List<User> users1 = testTableMapper.selectAll();

        System.out.println(users);
        System.out.println(users1);

        // 4. 释放资源
        sqlSession.close();
    }

    @Test
    public void testSelectById() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);
        List<User> users = testTableMapper.selectById(6);

        System.out.println(users);

        sqlSession.close();
    }

    @Test
    public void testSelectByCondition() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);

        //模拟前端接收到的数据
        int id = 7;
        double money = 10000;
        String password = "123456";
        //notice:因为条件查询中用到了like，需要对参数处理
        password = "%" + password + "%";

        //散装参数
/*
        List<User> users = testTableMapper.selectByCondition(id,money,password);
*/

        //notice 对象参数
        User user = new User();
        user.setId(id);
        user.setMoney(money);
        user.setPassword(password);
        List<User> users = testTableMapper.selectByCondition(user);

        //Map集合参数
/*
        Map map=new HashMap();
        map.put("id",id);
        map.put("money",money);
        map.put("password",password);
        List<User> users=testTableMapper.selectByCondition(map);
*/

        System.out.println(users);

        sqlSession.close();
    }

    @Test
    public void testAdd() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);

        double money = 10000;
        String password = "567895657";

        User user = new User(null, money, password);
        testTableMapper.add(user);
        //notice 要手动提交事务,或关闭事务：SqlSession sqlSession = sqlSessionFactory.openSession(true);开启自动提交事务
        sqlSession.commit();

        System.out.println(user.getId());

        sqlSession.close();
    }

    @Test
    public void testUpdateById() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);

        int id=12;
        double money = 66900;
        String password = "dhbgzrdfhbg";

        User user = new User(id, money, password);
        testTableMapper.updateById(user);

        System.out.println(user.getId());

        sqlSession.close();
    }

    @Test
    public void testDeleteByIds() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);

        int[] ids={10,14};

        testTableMapper.deleteByIds(ids);

        sqlSession.close();
    }

    /**
     * 使用注解实现增删改查
     */
    @Test
    public void testSelectById2() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        test_tableMapper testTableMapper = sqlSession.getMapper(test_tableMapper.class);

        int id =1;

        User user = testTableMapper.selectById2(id);

        System.out.println(user);

        sqlSession.close();
    }

}