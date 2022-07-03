package eventbus.simple;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
@Slf4j
public class SimpleEventBusExample {

    public static void main(String[] args) {
        // create event bus
        final EventBus eventBus = new EventBus();
        // register event listener
        eventBus.register(new SimpleListener());
        System.out.println("posting simple event");
        // post event
        eventBus.post("Simple event");
    }
}
