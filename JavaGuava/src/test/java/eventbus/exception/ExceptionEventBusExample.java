package eventbus.exception;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * SubscriberExceptionHandler的实现类：捕捉Subscriber的异常
 *
 * @Author: 赵鑫
 * @Date: 2022/7/10
 */
@Slf4j
public class ExceptionEventBusExample {
    @Test
    public void test(){
        // TODO: 如何同时定义EventBus的identifier和SubscriberExceptionHandler
        final EventBus eventBus=new EventBus(new ExceptionHandler());
        eventBus.register(new ExceptionListener());

        eventBus.post("exception bus");
    }

    @Test
    public void testWithJDK8(){
        final EventBus eventBus=new EventBus(((exception, context) -> {
            log.info("exception:    {}",exception.toString());
            log.info("eventBus:     {}",context.getEventBus());
            log.info("event:    {}",context.getEvent());
            log.info("subscriberMethod:     {}",context.getSubscriberMethod());
            log.info("subscriber:   {}",context.getSubscriber());
        }));
        eventBus.register(new ExceptionListener());

        eventBus.post("exception bus");
    }
}
@Slf4j
class ExceptionHandler implements SubscriberExceptionHandler {
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        log.info("exception:    {}",exception.toString());
        log.info("eventBus:     {}",context.getEventBus());
        log.info("event:    {}",context.getEvent());
        log.info("subscriberMethod:     {}",context.getSubscriberMethod());
        log.info("subscriber:   {}",context.getSubscriber());
    }
}
