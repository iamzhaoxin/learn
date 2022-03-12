package com.example.operation_maintain.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/12 20:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("test_table")
public class User extends Model<User> {
    private String id;
    private String money;
    @TableField(select = false)
    private String password;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
}
