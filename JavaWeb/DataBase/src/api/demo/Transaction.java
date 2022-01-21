package api.demo;

import api.connector.DbConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @About: 数据库事务管理
 * @Author: 赵鑫
 * @Date: 2022/1/20 16:30
 */
public class Transaction {
    public static void main(String[] args) throws Exception {

        // 获得连接
        Connection connection = DbConnector.getConnection();
        Statement statement = DbConnector.getStatement();

        // 查询余额
        ResultSet resultSet1 = statement.executeQuery("select money from test.test_table where id=1");
        resultSet1.next();
        double money1 = resultSet1.getDouble(1);

        ResultSet resultSet2 = statement.executeQuery("select money from test.test_table where id=2");
        resultSet2.next();
        double money2 = resultSet2.getDouble(1);

        //转账操作——练习事务管理
        try {
            // 开启事务
            connection.setAutoCommit(false);

            //操作事务
            String sql1 = "update test.test_table set money='" + (money1 - 100) + "' where id=1";
            statement.executeUpdate(sql1);
            String sql2 = "update test.test_table set money='" + (money2 + 100) + "' where id=2";
            statement.executeUpdate(sql2);

            //提交事务
            connection.commit();
            System.out.println("转账成功");
        } catch (Exception e) {
            //回滚事务
            connection.rollback();
            System.out.println("转账失败");
            e.printStackTrace();
        }

        DbConnector.close();
    }
}
