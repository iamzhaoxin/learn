package eventbus.deadEvent;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/10
 */
public class DeadListener {
    @Subscribe
    public void m1(DeadEvent event){
        System.out.println("event: "+event.getEvent());
        System.out.println("source: "+event.getSource());
    }
}
