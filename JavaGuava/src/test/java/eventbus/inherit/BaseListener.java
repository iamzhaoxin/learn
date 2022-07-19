package eventbus.inherit;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
@Slf4j
public class BaseListener extends CommonListener{

    public BaseListener() {
        System.out.println("BaseListener init");
    }

    @Subscribe
    public void baseTask(String event){
        log.info("[{}.{}] Received event [{}].",Thread.currentThread().getStackTrace()[1].getClassName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),event);
    }
}
