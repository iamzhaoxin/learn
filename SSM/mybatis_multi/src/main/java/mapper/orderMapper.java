package mapper;

import domain.Order;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/10 22:01
 */
public interface orderMapper {
    List<Order> findAll();
}
