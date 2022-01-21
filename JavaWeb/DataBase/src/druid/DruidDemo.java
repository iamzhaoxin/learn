package druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @About: Druid数据库连接池（学得不太透彻）
 * @Author: 赵鑫
 * @Date: 2022/1/21 10:19
 */
public class DruidDemo {
    public static void main(String[] args) throws Exception {
        //加载配置
        Properties properties=new Properties();
        properties.load(new FileInputStream("DataBase/src/druid/druid.properties"));
        //获得连接池对象
        DataSource dataSource= DruidDataSourceFactory.createDataSource(properties);

        //获得数据库连接
        Connection connection=dataSource.getConnection();

        System.out.println(connection);
    }
}
