package eventbus.events;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
@Slf4j
public class FruitEaterListener {

    @Subscribe
    public void eat(Fruit event){
        log.info("Fruit eat [{}]",event);
    }

    @Subscribe
    public void eat(Apple event){
        log.info("Apple eat [{}]",event);
    }
}
