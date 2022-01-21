package api.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/20 15:49
 */
public class DbConnector {
    private static String url;
    private static String user;
    private static String password;

    private static Connection connection;
    private static Statement statement;

    public static void setUrl(String url) {
        DbConnector.url = url;
    }

    public static void setUser(String user) {
        DbConnector.user = user;
    }

    public static void setPassword(String password) {
        DbConnector.password = password;
    }

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("api/connector/DbInfo");
            url = resourceBundle.getString("url");
            user = resourceBundle.getString("user");
            password = resourceBundle.getString("password");
        } catch (Exception e) {
            System.out.println("读取配置文件DbInfo.properties失败，不过还可以用setDb配置数据库~");
            e.printStackTrace();
        }
    }

    public static void setDb(String newUrl, String newUser, String newPassword) {
        url = newUrl;
        user = newUser;
        password = newPassword;
    }

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static Statement getStatement() throws Exception {
        connection = getConnection();
        if (statement == null) {
            statement = connection.createStatement();
        }
        return statement;
    }

    public static void close() throws Exception {
        if (statement != null) {
            statement.close();
            statement=null;
        }
        if (connection != null) {
            connection.close();
            connection=null;
        }
    }
}
