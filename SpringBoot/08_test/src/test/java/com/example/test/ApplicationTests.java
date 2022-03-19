package com.example.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(properties = {"test.prop=testValue2"})
@SpringBootTest(args = {"--test.prop=testValue3"})
class ApplicationTests {

    @Value("${test.prop}")
    private String msg;

    @Test
    void contextLoads() {
        System.out.println(msg);
    }

}
