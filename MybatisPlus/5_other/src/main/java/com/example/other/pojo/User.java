package com.example.other.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/10 22:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("test_table")
public class User extends Model<User>  {
    private Integer id;
    private String money;
    //notice 插入数据时填充
    @TableField(fill= FieldFill.INSERT)
    private String password;

    /*
        notice 逻辑删除
            为表增加字段deleted，用于表示数据是否被删除（1代表删除，0未删除）
            User实体中添加deleted属性并加注解
            配置中设置      logic-delete-value: 1    logic-not-delete-value: 0
     */
    @TableLogic
    private Integer deleted;
}
