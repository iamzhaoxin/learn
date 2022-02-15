package com.example.ssm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 15:09
 */

@Data
@TableName("test_table")
public class User {
    private String id;
    private String money;
    private String password;

}
