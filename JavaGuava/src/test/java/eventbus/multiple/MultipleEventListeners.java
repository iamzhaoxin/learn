package eventbus.multiple;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送String事件时
 * 所有参数是String的Listener都会接收到事件
 *
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */

@Slf4j
public class MultipleEventListeners {

    @Subscribe
    public void task1(String event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }

    @Subscribe
    public void task2(String event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
            Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }

    @Subscribe
    public void task3(Integer event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }
}
