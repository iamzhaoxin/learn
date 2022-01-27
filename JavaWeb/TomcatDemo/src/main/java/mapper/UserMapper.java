package mapper;

import pojo.User;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 21:51
 */
public interface UserMapper {
    List<User> selectAll();
}
