package eventbus.multiple;

import com.google.common.eventbus.EventBus;
import eventbus.simple.SimpleListener;

/**
 *  发送String事件时，
 *  所有参数是String的Listener都会接收到事件
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */

public class MultipleEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new MultipleEventListeners());
        System.out.println("posting simple event");
        eventBus.post("Simple event");
        eventBus.post(23333);
    }
}
