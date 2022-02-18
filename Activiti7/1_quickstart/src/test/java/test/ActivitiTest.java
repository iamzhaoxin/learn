package test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/18 21:24
 */
public class ActivitiTest {

    private static final ProcessEngine processEngine;

    static {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 部署
     * act_re_deployment 部署信息
     * act_re_procdef 流程定义的一些信息
     * act_ge_bytearray 流程定义的bpmn文件及png文件
     */
    @Test
    public void deployment() {
        // 得到 RepositoryService 实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/day.bpmn")
                .name("一天流程")
                .deploy();
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

    /**
     * 启动流程
     * act_hi_actinst     已完成的活动信息
     * act_hi_identitylink   参与者信息
     * act_hi_procinst   流程实例
     * act_hi_taskinst   任务实例
     * act_ru_execution   执行表
     * act_ru_identitylink   参与者信息
     * act_ru_task  当前任务
     */
    @Test
    public void startInstance() {
        RuntimeService runtime = processEngine.getRuntimeService();
        ProcessInstance process = runtime.startProcessInstanceByKey("dayAfterModify");

        System.out.println("流程部署的ID："+process.getDeploymentId());
        System.out.println("流程定义的Key："+process.getProcessDefinitionId());
        System.out.println("流程实例的ID："+process.getId());
    }

    /**
     * 查询个人待执行任务
     * select distinct RES.*
     * from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
     * WHERE RES.ASSIGNEE_ = '小一' and D.KEY_ = 'dayAfterModify'
     * order by RES.ID_ asc LIMIT 2147483647 OFFSET 0
     */
    @Test
    public void findPersonalTaskList(){
        TaskService taskService = processEngine.getTaskService();
        // 根据流程Key和任务负责人查询任务
        List<Task> tasks =taskService.createTaskQuery()
                .processDefinitionKey("dayAfterModify")
                .taskAssignee("小一")
                .list();
        for(Task task : tasks){
            System.out.println("流程实例ID="+task.getProcessInstanceId());      //mysql对应表：act_hi_procinst
            System.out.println("任务id="+task.getId());                        //mysql对应表：act_hi_taskinst
            System.out.println("任务负责人="+task.getAssignee());
            System.out.println("任务名称="+task.getName());
        }
    }

    /**
     * 完成任务
     * act_hi_actinst     已完成的活动信息
     * act_hi_identitylink   参与者信息
     * act_hi_taskinst   任务实例
     * act_ru_identitylink   参与者信息
     * act_ru_task  当前任务
     * 当我们一个流程走完后，关于一个流程实例的表中，处理hi的历史记录表，其余的act_ru_task,act_ru_execution等表会被清除
     */
    @Test
    public void completeAllTask() throws Exception {
        String processKey="dayAfterModify";
        String[] assignees={"小一","小二","小三"};
        for(String assignee:assignees){
            completeTask(processKey, assignee);
            sleep(3000);
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
