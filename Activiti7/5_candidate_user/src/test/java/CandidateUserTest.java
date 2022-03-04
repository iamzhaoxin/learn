import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import utils.ActivitiTools;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/3/4 15:21
 */
public class CandidateUserTest {


    private final String key="myProcess";
    private final ActivitiTools activitiTools=new ActivitiTools(key);

    //部署
    @Test
    void deployProcess(){
        Deployment deployment =activitiTools.getRepositoryService().createDeployment()
                .name(key)
                .addClasspathResource("bpmn/"+key+".bpmn")
                .addClasspathResource("bpmn/"+key+".png")
                .deploy();
        System.out.println("deployed: "+deployment.getName());
    }

    //删除
    @Test
    void deleteProcess(){
        activitiTools.getRepositoryService().deleteDeployment(activitiTools.getLatestProcessDefinition().getDeploymentId(),true);
    }

    //启动流程实例
    @Test
    void startProcessInstance(){
        activitiTools.getRuntimeService().startProcessInstanceByKey(key);
    }


    //查询组任务
    @Test
    void findGroupTask(){
        String candidateUser="李四";
        List<Task> taskList = activitiTools.getTaskService()
                .createTaskQuery()
                .taskCandidateUser(candidateUser)
                .list();
        for(Task task : taskList){
            System.out.println(task.getName());
        }
    }

    // 拾取任务
    @Test
    void claimTask(){
        String candidateUser="李四";
        Task task = activitiTools.getTaskService()
                .createTaskQuery()
                .taskCandidateUser(candidateUser)
                .singleResult();
        if(task != null){
            activitiTools.getTaskService().claim(task.getId(),candidateUser);
            System.out.println(candidateUser+" 拾取任务："+task.getName());
        }
    }

    //归还任务
    @Test
    void assigneeReturnTask(){
        String assignee="李四";
        Task task = activitiTools.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .singleResult();
        activitiTools.getTaskService().setAssignee(task.getId(),null);
        System.out.println(assignee+" 归还任务："+task.getName());
    }

    // 任务交接
    @Test
    void assigneeToAssignee(){
        String[] assignee={"李四","王五","noUser"};
        Task task = activitiTools.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee[1])
                .singleResult();
        activitiTools.getTaskService().setAssignee(task.getId(),assignee[2]);   // notice 这里不受限制，可以交给candidate-user之外的人
        System.out.println(assignee[0]+" 交接任务："+task.getName()+" 给："+assignee[1]);
    }


}
