import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ActivitiTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/21 21:18
 */
public class ListenerTest {
    private final String[] people = new String[]{"小艺", "小王", "小明"};
    private static ActivitiTools activiti;
    private static final String processKey="myProcess";

    @BeforeAll
    static void init() {
        activiti = new ActivitiTools(processKey);
    }

    @Test
    public void deployProcessTest() {
        Deployment deployment = activiti.getRepositoryService().createDeployment()
                .name(processKey)
                .addClasspathResource("bpmn/"+processKey+".bpmn")
                .addClasspathResource("bpmn/"+processKey+".png")
                .deploy();
        System.out.println("deploy process: " + deployment.getName());
    }

    @Test
    public void deleteProcess() {
        activiti.getRepositoryService()
                .deleteDeployment(activiti.getLatestProcessDefinition().getDeploymentId(), true);
    }

    @Test
    public void startInstanceTest() {
        if (activiti.getLatestProcessDefinition().isSuspended()) {
            System.out.println("this process has been suspend, can't start instance.");
        } else {
            activiti.getRuntimeService().startProcessInstanceByKey(processKey);
        }
    }

    @Test
    public void completeTask() {
        Task task=activiti.getTaskService().createTaskQuery()
                .processDefinitionKey(processKey)
//                .taskAssignee("小艺")
                .singleResult();
        if(!task.isSuspended()){
            activiti.getTaskService().complete(task.getId());
            System.out.println("complete "+task.getName());
        }
    }

}
