import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import utils.ActivitiTools;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/4 15:57
 */

/*
    notice
        并行网关： 不会解析条件，即使顺序流中定义了条件，也会被忽略
                 两个分支 通过 并行网关 合并成一个分支时，要等两个分支都完成任务才能继续
 */


public class ParallelGatewayTest {

}
