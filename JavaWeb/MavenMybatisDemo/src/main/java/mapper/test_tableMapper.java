package mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import pojo.User;

import java.util.List;
import java.util.Map;

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

    /**
     * notice 条件查询 参数接收
     *          1. 散装参数：多个参数时，用@Param(“SQL参数占位符名称”)
     *          2. 对象参数：对象的属性名称 要和 SQL参数占位符名称 一致
     *          3. Map集合参数：
     */
//    List<User> selectByCondition(@Param("id") int id,@Param("money") double money,@Param("password") String password);
    List<User> selectByCondition(User user);
//    List<User> selectByCondition(Map map);

    //添加
    void add(User user);

    //修改
    void updateById(User user);

    //批量删除
    void deleteByIds(@Param("ids") int[] ids);

    /**
     * notice 通过注解完成SQL语句
     * - 配置文件完成复杂功能
     * - 注解完成简单功能
     *    - 查找 @Select
     *    - 添加 @Insert
     *    - 修改 @Update
     *    - 删除 @Delete
     */
    @Select("select * from test.test_table where id = #{id};")
    @ResultMap("test_tableMap")
    User selectById2(int id);
}
