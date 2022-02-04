package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/4 17:31
 */

@PropertySource("classpath:jdbc.properties")    //加载配置文件,classpath:javac编译器的环境变量
public class DataSourceConfiguration {
    @Value("${jdbc.url")
    private String url;
    @Value("${jdbc.username")
    private String username;
    @Value("${jdbc.password")
    private String password;

    @Bean("dataSource")     //Spring将当前方法的返回值以指定名称储存到容器中
    public DataSource getDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
