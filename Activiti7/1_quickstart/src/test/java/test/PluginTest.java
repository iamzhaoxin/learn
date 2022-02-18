package test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import static java.lang.Thread.sleep;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/18 23:56
 */
public class PluginTest {
    private static final ProcessEngine processEngine;

    static {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void deployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/testPlugin.bpmn20.xml")
                .name("插件测试")
                .deploy();
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

    @Test
    public void startInstance() {
        RuntimeService runtime = processEngine.getRuntimeService();
        ProcessInstance process = runtime.startProcessInstanceByKey("testPlugin");

        System.out.println("流程部署的ID："+process.getDeploymentId());
        System.out.println("流程定义的Key："+process.getProcessDefinitionId());
        System.out.println("流程实例的ID："+process.getId());
    }

    @Test
    public void completeAllTask() throws Exception {
        String processKey="testPlugin";
        String[] assignees={"小一"};
        for(String assignee:assignees){
            completeTask(processKey, assignee);
            sleep(5000);
        }
    }
    public void completeTask(String processKey,String assignee){
        TaskService taskService=processEngine.getTaskService();
        // 查询任务
        Task task=taskService.createTaskQuery()
                .processDefinitionKey(processKey)
                .taskAssignee(assignee)
                .singleResult();
        if(task!=null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println(assignee+"完成任务:"+task.getName());
            System.out.println("task.getId() = " + task.getId());
        }
    }


}
