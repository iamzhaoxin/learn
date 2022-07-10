package eventbus.exception;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/10
 */

@Slf4j
public class ExceptionListener {
    @Subscribe
    public void m1(String msg){
        log.info("throw error");
        throw new RuntimeException("error msg");
    }
}
