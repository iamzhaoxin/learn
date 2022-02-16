package com.example.quickstart.pojo;

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
    private String money;
    private String password;
}
