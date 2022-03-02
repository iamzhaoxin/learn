import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import pojo.User;
import utils.ActivitiTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/2 11:09
 */
public class ProcessVariableTest {
    private final String key="variable";
    private final ActivitiTools activitiTools=new ActivitiTools(key);

    //部署
    @Test
    void deployProcess(){
        Deployment deployment =activitiTools.getRepositoryService().createDeployment()
                .name(key)
                .addClasspathResource("bpmn/"+key+".bpmn20.xml")
                .addClasspathResource("bpmn/"+key+".png")
                .deploy();
        System.out.println("deployed: "+deployment.getName());
    }

    //notice 启动任务时设置流程变量
    @Test
    void startProcess(){
        User user=new User();
        user.setFlag(10);
        Map<String,Object> variable=new HashMap<>();
//        variable.put("User",user);

        ProcessInstance processInstance = activitiTools.getRuntimeService()
                .startProcessInstanceByKey(key, variable);
        activitiTools.getRuntimeService().setProcessInstanceName(processInstance.getId(),key+"(InstanceName)");

        System.out.println("start instance: "+processInstance.getName());
    }

    //notice 任务办理时设置流程变量
    @Test
    void completeTask(){
        Task task =activitiTools.getTaskService().createTaskQuery()
                .processDefinitionKey(key)
                .singleResult();

        User user=new User();
        user.setFlag(-10);
        Map<String,Object> variable=new HashMap<>();
//        variable.put("User",user);

        activitiTools.getTaskService().complete(task.getId(),variable);
        System.out.println("complete task: "+task.getName());
    }

    //notice 通过任务设置流程变量
    @Test
    void setTaskVariables(){
        Task task = activitiTools.getTaskService().createTaskQuery().processDefinitionKey(key).singleResult();
        User user=new User(-6);
        activitiTools.getTaskService().setVariable(task.getId(),"User",user);
        Map<String, Object> variable = new HashMap<>();
        activitiTools.getTaskService().setVariables(task.getId(),variable);
    }

    //notice 通过流程实例设置流程变量
    @Test
    void runningTask(){
        Task task = activitiTools.getTaskService().createTaskQuery()
                .processDefinitionKey(key)
                .singleResult();
        String processInstanceId=task.getProcessInstanceId();

        User user=new User();
        user.setFlag(1);
        //设置单个流程变量（不用map）
        activitiTools.getRuntimeService().setVariable(processInstanceId,"User",user);
        //设置多个variable
        Map<String,Object> variable=new HashMap<>();
        variable.put("User",user);
//        activitiTools.getRuntimeService().setVariables("User",variable);
    }

    /*
        notice 办理任务前，设置local流程变量
            - 任务办理时设置local流程变量：只在当前实例的该任务结束前使用，任务结束后，变量无法使用，可通过查询历史任务查询
                taskService.setVariablesLocal(taskId,variables);    taskService.complete(taskId);
     */





}
