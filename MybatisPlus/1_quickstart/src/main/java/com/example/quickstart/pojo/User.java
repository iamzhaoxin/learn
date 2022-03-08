package com.example.quickstart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/16 21:34
 */
@Data
@TableName("test_table")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    @TableField(value = "money")    //指定数据表中的字段名（如果是驼峰命名，不需要指定，可自动匹配）
    private String userMoney;
    @TableField(select = false)     //select查询时不显示
    private String password;
    @TableField(exist = false)      //在数据表中是不存在的
    private String unExistValue;
}
