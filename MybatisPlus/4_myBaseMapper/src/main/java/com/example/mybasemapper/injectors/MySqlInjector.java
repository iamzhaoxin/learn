package com.example.mybasemapper.injectors;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 20:48
 */

// notice 自定义SQL语句注入（如果直接继承AbstractSqlInjector，会导致DefaultSqlInjector中自带的方法失效）
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        //获取父类集合
        List<AbstractMethod> list = new ArrayList<>(super.getMethodList(mapperClass, tableInfo));
        //扩充自定义方法
        list.add(new FindAll());
        return list;
    }
}
