package eventbus.simple;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
@Slf4j
public class SimpleListener {

    /**
     * the subscriber object is a normal class
     * the subscribe method must public and void return value
     * the subscribe method must have only one argument
     * the subscribe method must be annotated by @Subscribe
     */
    @Subscribe
    public void doAction(final String event) {
        log.info("[{}] received event [{}] and taking action.", this.getClass(), event);
    }
}
