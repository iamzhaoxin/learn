package handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/10 19:23
 */
public class MoneyTypeHandler extends BaseTypeHandler<Double> {

    /**
     * 将Java类型转为数据库需要的类型
     * @param i: 自定义Type的位置
     * @param o: 传入的需要被转换的Java类型
     * @param jdbcType
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Double o, JdbcType jdbcType) throws SQLException {
        //将Double o转换为自定义Type, 本实例将其转换为美元,汇率6.36,java对象是Double,自定义Type是Float
        float dollar= (float) (o/6.36);
        preparedStatement.setFloat(i,dollar);
    }

    /**
     * 数据库中的类型转换为Java对象
     * @param resultSet 查询结果集
     * @param s 要转换的字段的名称
     * @return 返回Java对象
     */
    @Override
    public Double getNullableResult(ResultSet resultSet, String s) throws SQLException {
        float dollar = resultSet.getFloat(s);
        return (double) dollar*6.36;
    }

    /**
     * 数据库中的类型转换为Java对象
     * @param resultSet 结果集
     * @param i 要转换字段在结果集中的位置i
     * @return Java对象
     */
    @Override
    public Double getNullableResult(ResultSet resultSet, int i) throws SQLException {
        float dollar = resultSet.getFloat(i);
        return (double) dollar*6.36;
    }

    /**
     * 数据库中的类型转换为Java对象
     * @return Java对象
     */
    @Override
    public Double getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        float dollar = callableStatement.getFloat(i);
        return (double) dollar*6.36;
    }
}
