package pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/2 11:21
 * fixme 如果将pojo存到流程变量中，必须实现序列化接口serializable
 *          为了防止由于新增字段无法反序列化，需要生成serialVersionUID
 */
@Data
public class User implements Serializable {
    int flag;

    public User() {
    }

    public User(int flag) {
        this.flag = flag;
    }
}
