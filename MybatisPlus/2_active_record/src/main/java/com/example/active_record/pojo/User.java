package com.example.active_record.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
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
// notice 开启AR：继承 Model<T>
public class User extends Model<User>  {
    private Integer id;
    private String money;
    private String password;
}
