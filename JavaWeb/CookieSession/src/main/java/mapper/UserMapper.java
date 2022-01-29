package mapper;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import pojo.User;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 21:51
 */
public interface UserMapper {

    List<User> selectAll();

    @Select("select * from test.test_table where id=#{id};")
    @ResultMap("UserMap")
    User selectById(int id);
}
