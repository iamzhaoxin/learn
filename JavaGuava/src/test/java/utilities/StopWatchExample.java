package utilities;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/2
 */
public class StopWatchExample {

    private final static Logger LOGGER= LoggerFactory.getLogger(StopWatchExample.class);

    public static void main(String[] args) throws InterruptedException {
        process("333333");
    }

    private static void process(String orderNo) throws InterruptedException {
        LOGGER.info("start process the order [{}]",orderNo);
        // 开始计时
        Stopwatch stopwatch= Stopwatch.createStarted();

        TimeUnit.SECONDS.sleep(1);
        // 停止计时
        LOGGER.info("The orderNo [{}] process successful and elapsed [{}]",orderNo,stopwatch.stop());
    }
}
