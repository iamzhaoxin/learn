package eventbus.communicate;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.Request;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/10
 */
public class ComEachOtherExample {
    public static void main(String[] args) {
        final EventBus eventBus=new EventBus();
        QueryService queryService=new QueryService(eventBus);
        eventBus.register(new RequestHandler(eventBus));
        queryService.query("55555");

    }
}

@Slf4j
class RequestHandler{

    private final EventBus eventBus;

    RequestHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void deQuery(Integer request){
        log.info("receive request: {}",request.toString());
        String response="response";
        this.eventBus.post(response);
    }
}

@Slf4j
class QueryService{
    private final EventBus eventBus;

    QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void query(String orderNo){
        log.info("receive OrderNo.{}",orderNo);
        this.eventBus.post(Integer.valueOf(orderNo));
    }

    @Subscribe
    public void handleResponse(String response){
        log.info("receive response: {}",response);
    }
}
