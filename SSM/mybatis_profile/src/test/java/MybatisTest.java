import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.test_tableMapper;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.*;
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
        test_tableMapper test_tableDao=sqlSession.getMapper(test_tableMapper.class);

        //设置分页相关参数 当前页/每页显示的条数
        PageHelper.startPage(1,3);

        List<User> users = test_tableDao.selectAll();

        for (User user:users){
            System.out.println(user);
        }
        //获得分页相关参数
        PageInfo<User> pageInfo= new PageInfo<>(users);
        System.out.println("当前页: "+pageInfo.getPageNum());
        System.out.println("下一页: "+pageInfo.getNextPage());
        System.out.println(pageInfo);

        sqlSession.close();
    }

}
