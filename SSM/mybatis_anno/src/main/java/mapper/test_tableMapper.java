package mapper;

import domain.Order;
import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/10 21:50
 */

public interface test_tableMapper {


    @Select("select * from test.test_table;")
    @Results({
            /* id=true:主键*/
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "password",property = "password"),
            @Result(
                    property = "orderList",
                    column = "id",
                    /*notice 返回的是List类型*/
                    javaType = List.class,
                    many=@Many(select = "mapper.orderMapper.findByUid")
            )
    })
    List<User> AnnoFindAll();

    @Select("select * from test.test_table where id=#{id}")
    User findById(int id);
}
