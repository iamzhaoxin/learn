package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/11 22:19
 */
public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取连接
        String url = "jdbc:mysql://82.157.138.64:3306";
        String userName = "root";
        String passWord = "123456";
        Connection connection = DriverManager.getConnection(url,userName,passWord);

        // 3. 定义SQL
        String sql = "update test.test_table set money = 555000 where id =1";
        // 4. 获得执行SQL的对象
        Statement statement =connection.createStatement();
        // 5. 执行SQL
        int count =statement.executeUpdate(sql);
        // 6. 处理结果
        System.out.println(count);
        // 7. 释放资源
        statement.close();
        connection.close();
    }
}
