package com.example.quickstart.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quickstart.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest(classes = Application.class)
@SpringBootTest()
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void testInsert() {
        User user = new User(null, "1000000", "password_1", "unExistedValue");
        int resultLines = userDao.insert(user);
        System.out.println(resultLines);
    }

    @Test
    void testUpdateById() {
        User user = new User("22", "0", "nopassword", "a");
        System.out.println(userDao.updateById(user));
    }

    @Test
    void testUpdateByCondition() {
        User user = new User(null, "0", "yespassword？", "a");
        QueryWrapper<User> wrapper = new QueryWrapper<>();    //只是查询条件，没有set
        wrapper.like("money", "3");  //column是数据库字段，不是pojo对象的属性
        System.out.println(userDao.update(user, wrapper));
    }

    @Test
    void testUpdateByCondition2() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("money", "-500").like("password", "yes");   //直接更新，不需要new一个User对象设置更新后的值
        System.out.println(userDao.update(null, wrapper));
    }

    @Test
    void testDeleteByCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "22");
        map.put("password", "yespassword？"); //多个条件之间是and关系
        System.out.println(userDao.deleteByMap(map));
    }

    @Test
    void testDeleteByCondition2() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("password", "99999")
                .eq("money", "0");
        //另一种：根据对象生成Wrapper
        User user = new User(null, "-500", null, null);
        QueryWrapper<User> wrapper2 = new QueryWrapper<>(user);

        System.out.println(userDao.delete(wrapper));
        System.out.println(userDao.delete(wrapper2));
    }

    @Test
    void testDeleteBatchIds() {
        System.out.println(userDao.deleteBatchIds(Arrays.asList(16, 18)));
    }

    // 用selectOne()查询单条数据时，如果根据查询条件找到了多条数据，会报错

    @Test
    void testSelectByPage() {
        int currentPage = 1;
        int pageSize = 10;
        Page<User> page = new Page<>(currentPage, pageSize);

        User user = new User(null, "1000000", null, null);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

        Page<User> userPage = userDao.selectPage(page, wrapper);
        System.out.println(userPage.getRecords());
        System.out.println("数据总条数: " + userPage.getTotal());
    }

    /**
     * 测试使用自定义xml文件和接口代理
     */
    @Test
    void testCustomXml() {
        User user=userDao.findById(23);
        System.out.println(user);
    }

    @Test
    void testOrOrderDesc() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // OR 逻辑
        wrapper.eq("id","22").or().eq("password","123456");
        // 排序
        wrapper.orderByDesc("id");
        // 只返回指定字段
        wrapper.select("id","money");

        List<User> userList = userDao.selectList(wrapper);
        for(User user : userList){
            System.out.println(user);
        }
    }

}
