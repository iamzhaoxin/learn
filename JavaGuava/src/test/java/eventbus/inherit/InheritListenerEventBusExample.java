package eventbus.inherit;

import com.google.common.eventbus.EventBus;

/**
 * 父类Listener也会被注册
 *
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
public class InheritListenerEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus=new EventBus();
        eventBus.register(new ConcreteListener());
        eventBus.post("i am a event");
        eventBus.post(666);
        /*
         * TODO
         *  输出顺序为什么是
         *  CommonListener
         *  ConcreteListener
         *  BaseListener
         */
    }
}
