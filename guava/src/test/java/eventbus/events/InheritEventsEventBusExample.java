package eventbus.events;

import com.google.common.eventbus.EventBus;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
public class InheritEventsEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus=new EventBus();
        eventBus.register(new FruitEaterListener());
        // 发送子类事件时，父类子类监听器都会收到消息
        eventBus.post(new Apple("apple"));
        System.out.println("========");
        // 发送父类事件，只有父类监听器收到消息
        eventBus.post(new Fruit("fruit"));
    }
}
