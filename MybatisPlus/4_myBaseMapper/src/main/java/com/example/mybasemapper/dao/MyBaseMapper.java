package com.example.mybasemapper.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/11 20:46
 */

// notice 自定义baseMapper，包含了原生的BaseMapper
public interface MyBaseMapper <T> extends BaseMapper<T> {
    List<T> findAll();
}
