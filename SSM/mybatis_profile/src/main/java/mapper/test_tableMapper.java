package mapper;

import domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @About: Mapper 代理接口
 * @Author: 赵鑫
 * @Date: 2022/1/22 22:28
 */
public interface test_tableMapper {

    //查询test.test_table表的所有内容
    List<User> selectAll();

    //根据id查询
    List<User> selectById(int id);

    //条件查询
    List<User> selectByCondition(User user);

    //添加
    void add(User user);

    //修改
    void updateById(User user);

    //批量删除
    void deleteByIds(@Param("ids") int[] ids);

    @Select("select * from test.test_table where id = #{id};")
    @ResultMap("test_tableMap")
    User selectById2(int id);
}
