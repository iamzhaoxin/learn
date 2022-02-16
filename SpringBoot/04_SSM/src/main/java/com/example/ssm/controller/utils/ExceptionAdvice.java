package com.example.ssm.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/16 10:22
 */
@RestControllerAdvice   //作为SpringMVC的异常处理器
public class ExceptionAdvice {

    @ExceptionHandler   //拦截所有异常
    public R doException(Exception ex){
        //记录日志.通知运维.通知开发
        return new R(false,"error");
    }
}
