import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ActivitiTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/20 22:12
 */
public class TaskListenerTest {
    private final String[] people = new String[]{"小明", "小王", "小艺"};
    private static ActivitiTools activiti;
    private static final String processKey="schoolDay"; //todo: revised file name

    @BeforeAll
    static void init() {
        activiti = new ActivitiTools(processKey);
    }

    @Test
    public void deployProcessTest() {
        Deployment deployment = activiti.getRepositoryService().createDeployment()
                .name(processKey)
                .addClasspathResource("bpmn/"+processKey+".bpmn20.xml")
                .addClasspathResource("bpmn/"+processKey+".png")
                .deploy();
        System.out.println("deploy process: " + deployment.getName());
    }

    @Test
    public void deleteProcess() {
        activiti.getRepositoryService()
                .deleteDeployment(activiti.getLatestProcessDefinition().getDeploymentId(), true);
    }

    /**
     * 指定任务负责人 & 启动流程实例
     */
    @Test
    public void startInstanceTest() {
        if (activiti.getLatestProcessDefinition().isSuspended()) {
            System.out.println("this process has been suspend, can't start instance.");
        } else {
            // todo
        }
    }

}