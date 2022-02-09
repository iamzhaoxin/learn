package jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.annotation.Target;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 13:54
 */
public class JdbcTemplateTest {

    private final ApplicationContext app = new ClassPathXmlApplicationContext("application.xml");

    @Test
    /* 测试JdbcTemplate开发步骤 */
    public void test() {
        //数据源对象
        ComboPooledDataSource dataSource = (ComboPooledDataSource) app.getBean("dataSource");

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        //设置数据源
        jdbcTemplate.setDataSource(dataSource);
        //执行操作
        int row = jdbcTemplate.update("insert into test.test_table values (?,?,?)", null, 999, 123654);
        System.out.println(row);
    }

    @Test
    /*测试Spring产生JdbcTemplate对象*/
    public void test2() {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) app.getBean("jdbc");
        System.out.println(jdbcTemplate.update("insert into test.test_table values (?,?,?)", null, 999, 123654));
    }

    
}
