package eventbus.inherit;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */

@Slf4j
public abstract class CommonListener {
    @Subscribe
    public void commonTask(String event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }

    @Subscribe
    public void commonIntTask(Integer event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }
}
