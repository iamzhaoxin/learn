package mapper;

import domain.Order;
import domain.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/10 22:01
 */
public interface orderMapper {

    @Select("select * from test.test_table user,test.`order` `order` where user.id=`order`.uid")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(column = "uid", property = "user.id"),
            @Result(column = "money", property = "user.money"),
            @Result(column = "password", property = "user.password")
    })
    List<Order> AnnoFindAll();

    @Select("select * from test.`order`")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(
                    property = "user",    //要封装的Order中的属性名称
                    column = "uid",  //根据order表中哪个字段查询test_table表中的数据
                    javaType = domain.User.class,   //要封装的实体类型
                    //select属性,代表查询哪个接口的方法获得数据
                    one = @One(select = "mapper.test_tableMapper.findById")
            )
    })
    List<Order> AnnoFindAll2();

    @Select("select * from test.`order` where uid=#{uid}")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(
                    property = "user",    //要封装的Order中的属性名称
                    column = "uid",  //根据order表中哪个字段查询test_table表中的数据
                    javaType = domain.User.class,   //要封装的实体类型
                    //select属性,代表查询哪个接口的方法获得数据
                    one = @One(select = "mapper.test_tableMapper.findById")
            )
    })
    List<Order> findByUid(int uid);
}
