package dataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/3 20:30
 */
public class DataSourceTest {
    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    /*手动配置连接数据源*/
    @Test
    public void manualDataSource() throws Exception {
        //创建数据源对象
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //手动配置连接参数(或读取xml)
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://82.157.138.64:3306");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        //创建连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /*spring配置数据源*/
    @Test
    public void springDataSource() throws Exception {
        ComboPooledDataSource dataSource = (ComboPooledDataSource) applicationContext.getBean("dataSource");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        ComboPooledDataSource dataSource2 = (ComboPooledDataSource) applicationContext.getBean("dataSource2");
        Connection connection2 = dataSource2.getConnection();
        System.out.println(connection2);
    }
}
