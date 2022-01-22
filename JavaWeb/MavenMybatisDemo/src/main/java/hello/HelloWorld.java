package hello;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/22 9:47
 */
public class HelloWorld {
    public static final Logger LOGGER = LoggerFactory.getLogger("HelloWorld.class");

    public static void main(String[] args) {
        LOGGER.trace("i am just a trace");
        LOGGER.debug("i am a logger debug");
        LOGGER.info("i am a logger info");
        LOGGER.warn("i am a logger warn");
        LOGGER.error("i am a logger error");
        System.out.println("HelloWorld");
    }
}
