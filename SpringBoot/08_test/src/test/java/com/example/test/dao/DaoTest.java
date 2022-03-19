package com.example.test.dao;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/19 23:17
 */
@SpringBootTest
@Transactional  //notice 测试dao层时，回滚事务，避免向数据库中添加测试数据
public class DaoTest {

}
