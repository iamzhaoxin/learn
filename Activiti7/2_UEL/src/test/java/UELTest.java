import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ActivitiTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/20 21:05
 */
public class UELTest {

    private final String[] people = new String[]{"小明", "小王", "小艺"};
    private static ActivitiTools activiti;

    @BeforeAll
    static void init() {
        activiti = new ActivitiTools("schoolDay");
    }

    @Test
    public void deployProcessTest() {
        Deployment deployment = activiti.getRepositoryService().createDeployment()
                .name("学校的一天")
                .addClasspathResource("bpmn/schoolDay.bpmn20.xml")
                .addClasspathResource("bpmn/schoolDay.png")
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
            Map<String, Object> map = new HashMap<>();
            map.put("wakeup", people[1]);
            map.put("student", people[0]);
            String businessId ="otherTable";
            ProcessInstance processInstance = activiti.getRuntimeService()
                    .startProcessInstanceByKey(activiti.getProcessKey(),businessId, map);
            System.out.println("start instance success, instance id: " + processInstance.getId());
        }
    }

}
