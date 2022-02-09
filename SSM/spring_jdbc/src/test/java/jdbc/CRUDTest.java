package jdbc;

import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 14:28
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public class CRUDTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testUpdate() {
        jdbcTemplate.update("update test.test_table set money=? where id=?", 10000, 5);
    }

    @Test
    public void testDelete() {
        jdbcTemplate.update("delete from test.test_table where id=?", 5);
    }

    @Test
    public void testQuarryAll() {
        /* new BeanPropertyRowMapper<User>(User.class): 初始化一个实现类BeanPropertyRowMapper对象,<泛型>(泛型的字节码对象)*/
        List<User> userList = jdbcTemplate.query(
                "select * from test.test_table", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(userList);
    }

    @Test
    public void testQuarryOne() {
        /*如果查询的id不存在,会报错,可以查询select count(*)判断,如果count>0再执行查询*/
        User user = jdbcTemplate.queryForObject(
                "select * from test.test_table where id=?", new BeanPropertyRowMapper<User>(User.class), 9);
        System.out.println(user);
    }

    @Test
    public void testCount() {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from test.test_table where id=?", Integer.class, 9);
        System.out.println(count);
    }
}
