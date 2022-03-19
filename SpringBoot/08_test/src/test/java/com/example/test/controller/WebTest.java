package com.example.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/19 22:47
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //启动端口
@AutoConfigureMockMvc   // 开启虚拟MVC调用
public class WebTest {

    @Test
    void testPort(){

    }

    @Test
    void testWebStatus(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder= MockMvcRequestBuilders.get("/users");   //创建虚拟请求
        ResultActions action = mvc.perform(builder);//执行对应请求

        // 设置预期值，与真实值比较
        StatusResultMatchers status = MockMvcResultMatchers.status();   //定义预期值
        ResultMatcher ok=status.isOk(); //预期调用成功的状态是200
        action.andExpect(ok);       //将预期值添加到调用，和结果匹配

    }

    @Test
    void testWebContent(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder= MockMvcRequestBuilders.get("/users");   //创建虚拟请求
        ResultActions action = mvc.perform(builder);//执行对应请求

        // 设置预期值，与真实值比较
        ContentResultMatchers content = MockMvcResultMatchers.content();   //定义预期值
        ResultMatcher result=content.string("123");
        ResultMatcher result2=content.json("{\"id\":1}");    //比较json结果
        action.andExpect(result);       //将预期值添加到调用，和结果匹配

    }
}
