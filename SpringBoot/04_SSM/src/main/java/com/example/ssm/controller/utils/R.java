package com.example.ssm.controller.utils;

import lombok.Data;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 22:16
 */
@Data
public class R {
    private Boolean flag;
    private Object data;

    public R(Boolean flag) {
        this.flag = flag;
    }

    public R(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }
}
