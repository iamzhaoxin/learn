package basic.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxin
 */
public class Logback {
    //public static final Logger LOGGER = LoggerFactoy.getLogger("Logback.class");  //因为少了一个 r ，崩溃两个小时
    public static final Logger LOGGER = LoggerFactory.getLogger("Logback.class");

    public static void main(String[] args) {
        try {
            LOGGER.debug("main开始执行");
            LOGGER.info("这是一条info日志");
            int a = 10, b = 0;
            LOGGER.trace("b= " + a);

            System.out.println(a / b);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出现异常： " + e);
        }
    }
}
