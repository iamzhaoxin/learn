package com.example.operation_maintain_log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j  // notice 这个注解代替了“private static final Logger log= LoggerFactory.getLogger(ApplicationTests.class);”
@SpringBootTest
class ApplicationTests {
    @Test
    void contextLoads() {
        log.error("message");
    }

}
