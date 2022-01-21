package api.demo;

import api.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/20 23:30
 */
public class Inject {
    public static void main(String[] args) throws Exception {
        Statement statement = DbConnector.getStatement();

        // 注入
        int id = 1;
        String password = "' or '1'='1";
        String sql = "select * from test.test_table where id='" + id + "' and password='" + password + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            System.out.println("login success!");
        } else {
            System.out.println("login failure!");
        }
        DbConnector.close();


        //防注入(PreparedStatement对敏感字符进行转义）

        /**
         * DbConnector.setUrl("jdbc:mysql://82.157.138.64:3306?useServerPrepStmts=true");
         * 由于大多数据情况下数据库连接参数中并不会配置useServerPrepStmts = true，
         * 此时应用程序工作在客户端的预编译模式下，性能与Statement相比未有明显提高，
         * 尽管开启服务端预编译能提升吞吐量，但该方式存在过多的BUG，在生产环境中仍然不建议开启，避免采坑
         */
        Connection connection = DbConnector.getConnection();

        String sql2 = "select * from test.test_table where id=? and password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, password);

        ResultSet resultSet1 = preparedStatement.executeQuery();

        if (resultSet1.next()) {
            System.out.println("login success!");
        } else {
            System.out.println("login failure!");
        }
        DbConnector.close();
    }
}
