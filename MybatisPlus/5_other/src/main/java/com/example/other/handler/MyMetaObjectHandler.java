package com.example.other.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 22:00
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 插入数据时填充
    @Override
    public void insertFill(MetaObject metaObject) {
        Object password = getFieldValByName("password",metaObject);
        if(password==null){
            setFieldValByName("password","888888",metaObject);
        }
    }

    // 更新数据时填充
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}