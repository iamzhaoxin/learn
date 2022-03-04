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
        不用排他网关也可以实现分支（连线的condition条件上 设置分支条件）
            - 条件都不满足时，流程异常结束
        使用排他网关（ExclusiveGateway）
            - 都满足时，选id较小的分支执行
            - 都不满足时，抛出异常
 */


public class ExclusiveGatewayTest {
    String key="MyProcessExclusiveGateway";
    ActivitiTools activitiTools=new ActivitiTools(key);

    //部署
    @Test
    void deploy(){
        activitiTools.getRepositoryService().createDeployment()
                .addClasspathResource("bpmn/"+key+".bpmn")
                .addClasspathResource("bpmn/"+key+".png")
                .name(key)
                .deploy();
        System.out.println("deploy success");
    }

    //启动
    @Test
    void startProcessInstance(){
        ProcessInstance instance =activitiTools.getRuntimeService().startProcessInstanceByKey(key);
        activitiTools.getRuntimeService().setVariable(instance.getId(),"flag","-5");
    }

    @Test
    void completeTask(){
        String assignee="测试员";
        Task task = activitiTools.getTaskService().createTaskQuery().taskAssignee(assignee).singleResult();
        activitiTools.getTaskService().complete(task.getId());
    }
}
