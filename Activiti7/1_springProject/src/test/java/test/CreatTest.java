package test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/17 15:25
 */
public class CreatTest {

    /**
     * notice 使用activiti提供的默认方式创建MySQL表
     */
    @Test
    public void creatDataTableTest(){
        //使用activiti提供的工具类ProcessEngines的getDefaultProcessEngine方法,默认从resources下读取activiti.cfg.xml文件
        //创建processEngine时,就会创建数据库的表
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

        System.out.println(processEngine);
    }
}
