package eventbus.deadEvent;

import com.google.common.eventbus.EventBus;

/**
 * 当EventBus发布了一个事件，但是注册的订阅者中没有找到处理该事件的方法，
 * 那么EventBus就会把该事件包装成一个DeadEvent事件来重新发布
 *
 * @Author: 赵鑫
 * @Date: 2022/7/10
 */
public class DeadEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus=new EventBus("DeadEventBus"){
            @Override
            public String toString(){
                return "DEAD-EVENT-BUS";
            }
        };
        DeadListener deadListener=new DeadListener();
        eventBus.register(deadListener);
        eventBus.post("hello");
        eventBus.unregister(deadListener);
        eventBus.post("bye");
    }
}
