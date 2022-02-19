package test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/18 21:24
 */
public class ActivitiTest {

    private static final ProcessEngine processEngine;

    private final String processKey = "dayAfterModify";
    private final String[] assignees = {"小一", "小二", "小三"};

    static {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    public ProcessDefinition getDeploymentDefinitionByKey(String processDefinitionKey) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion()
                .desc()
//                .asc()
                .list();
        return processDefinitionList.get(0);
    }

    /**
     * notice 部署
     * 获取ProcessEngine对象
     * 得到RepositoryService实例
     * 进行部署
     * <p>
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
                .addClasspathResource("bpmn/day.png")
                .name("一天流程")
                .deploy();
        // 通过zip部署
        /*
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("bpmn/day.zip");  //使用相对路径寻找资源
        Deployment deploymentByZip = repositoryService.createDeployment()
                .addZipInputStream(new ZipInputStream(inputStream))
                .deploy();
        */
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

    /**
     * notice 启动流程
     * 获取ProcessEngine对象
     * 获取runtimeService对象
     * 创建流程实例,流程定义的key需要知道
     * <p>
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
        ProcessInstance process = runtime.startProcessInstanceByKey(processKey);

        System.out.println("流程部署的ID：" + process.getDeploymentId());
        System.out.println("流程定义的Key：" + process.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + process.getId());
    }

    /**
     * notice 完成任务
     * 得到ProcessEngine对象
     * 得到TaskService对象
     * 查询任务
     * 判断
     * 完结任务
     * <p>
     * act_hi_actinst     已完成的活动信息
     * act_hi_identitylink   参与者信息
     * act_hi_taskinst   任务实例
     * act_ru_identitylink   参与者信息
     * act_ru_task  当前任务
     * 当我们一个流程走完后，关于一个流程实例的表中，处理hi的历史记录表，其余的act_ru_task,act_ru_execution等表会被清除
     */
    @Test
    public void completeAllTask() throws Exception {
        for (String assignee : assignees) {
            completeTask(processKey, assignee);
            sleep(3000);
        }
    }

    public void completeTask(String processKey, String assignee) {
        TaskService taskService = processEngine.getTaskService();
        // 查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processKey)
                .taskAssignee(assignee)
                .singleResult();
        if (task != null) {
            // 完成任务
            taskService.complete(task.getId());
            System.out.println(assignee + "完成任务:" + task.getName());
            System.out.println("task.getId() = " + task.getId());
        }
    }

    /**
     * notice 查询个人待执行任务
     * 得到ProcessEngine对象
     * 得到TaskService对象
     * 根据流程定义的key负责人assignee来实现当前用户的人物列表查询
     * <p>
     * select distinct RES.*
     * from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
     * WHERE RES.ASSIGNEE_ = '小一' and D.KEY_ = 'dayAfterModify'
     * order by RES.ID_ asc LIMIT 2147483647 OFFSET 0
     */
    @Test
    public void findPersonalTaskList() {
        TaskService taskService = processEngine.getTaskService();
        // 根据流程Key和任务负责人查询任务
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(processKey)
                .taskAssignee(assignees[0])
                .list();
        for (Task task : tasks) {
            System.out.println("流程实例ID=" + task.getProcessInstanceId());      //mysql对应表：act_hi_procinst
            System.out.println("任务id=" + task.getId());                        //mysql对应表：act_hi_taskinst
            System.out.println("任务负责人=" + task.getAssignee());
            System.out.println("任务名称=" + task.getName());
        }
    }

    /**
     * notice 查询流程的定义信息
     * 获取processEngine对象
     * 创建RepositoryService对象
     * 得到ProcessDefinitionQuery对象
     * 设置条件，并查询当前的所有流程定义
     */
    @Test
    public void findProcessDefinitionTest() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 得到ProcessDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        // 设置条件,查询一个
        ProcessDefinition processDefinition = processDefinitionQuery
                .processDefinitionKey(processKey)
                .orderByProcessDefinitionVersion()
                .desc()
                .singleResult();
        System.out.println("\n查询一个:");
        System.out.println("流程定义ID：" + processDefinition.getId());
        System.out.println("流程定义名称：" + processDefinition.getName());
        System.out.println("流程定义的Key：" + processDefinition.getKey());
        System.out.println("流程定义的版本号：" + processDefinition.getVersion());
        System.out.println("流程部署的ID:" + processDefinition.getDeploymentId());

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .processDefinitionKey(processKey)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();

        System.out.println("\n\n\n 查询多个:");
        for (ProcessDefinition _processDefinition : processDefinitionList) {
            System.out.println("流程定义ID：" + _processDefinition.getId());
            System.out.println("流程定义名称：" + _processDefinition.getName());
            System.out.println("流程定义的Key：" + _processDefinition.getKey());
            System.out.println("流程定义的版本号：" + _processDefinition.getVersion());
            System.out.println("流程部署的ID:" + _processDefinition.getDeploymentId());

        }
    }

    /**
     * notice 查看流程执行的历史数据
     * 获取processEngine对象
     * 得到historyService
     * 得到historicActivityInstanceQuery对象
     * 执行查询操作
     * 遍历得到结果
     */
    @Test
    public void actionHistoryTest() {
        // 获取流程实例的id
        TaskService taskService = processEngine.getTaskService();
        // 根据流程Key和任务负责人查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processKey)
                .taskAssignee(assignees[0])
                .singleResult();
//        String processInstanceId = task.getId();
        // 查看流程实例的历史数据
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //      执行查询操作，更具时间排序
        List<HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery
                .processInstanceId("2501")  //fixme 怎么得到的id
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        //      遍历得到结果并输出
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            System.out.println(historicActivityInstance.getActivityId());          //id
            System.out.println(historicActivityInstance.getActivityName());        //name
            System.out.println(historicActivityInstance.getProcessDefinitionId()); //实例id
            System.out.println(historicActivityInstance.getProcessInstanceId());   //流程实例的Id
            System.out.println("-------------------------------------------------");
        }
    }

    /**
     * notice 删除流程定义
     * 获取processEngine对象
     * 创建RepositoryService对象
     * 获取流程部署的Id
     * 执行删除流程定义
     */
    @Test
    public void deleteProcessDefinitionTest() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 获取流程部署id
        String processDefinitionId = getDeploymentDefinitionByKey(processKey).getDeploymentId();
        // 删除流程定义 第二个参数true:级联删除,会先删除没有完成的流程结点，最后就可以删除流程定义信息    默认false
        repositoryService.deleteDeployment(processDefinitionId, true);
    }

    /**
     * notice 从数据库获取资源文件
     * 方案一: 使用Activiti提供的API下载文件
     * 方案二: 用代码从数据库下载
     *      获取processEngine对象
     *      获取RepositryService对象
     *      得到ProcessDefinitionQuery
     *      设置查询条件
     *      执行查询操作，查询出流程定义
     *      根据流程定义信息，得到部署的id
     *      输入流InpueStream读取bpmn文件信息
     *      输出流OutputStream
     *      IO流转换
     *      关闭流
     */
    @Test
    public void getFileTest() throws Exception {
        //根据key获取流程部署信息
        ProcessDefinition processDefinition = getDeploymentDefinitionByKey(processKey);
        String pngName = processDefinition.getDiagramResourceName();
        String xmlName = processDefinition.getResourceName();
        String deploymentId = processDefinition.getDeploymentId();
        // 根据流程部署id和资源名,获取输入流
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String path=System.getProperty("user.dir");
        if (pngName != null) {
            InputStream pngInputStream = repositoryService.getResourceAsStream(deploymentId, pngName);
            // 构造输出流
            File file=new File(path+"\\download\\"+pngName);
            if(!file.exists()){
                // 先得到文件的上级目录，并创建上级目录
                boolean mkdirs = file.getParentFile().mkdirs();
                // 再创建文件
                boolean newFile = file.createNewFile();
            }
            OutputStream pngOutputStream = new FileOutputStream(file);
            // 输入流到输出流的转换
            IOUtils.copy(pngInputStream, pngOutputStream);
            pngOutputStream.close();
            pngInputStream.close();
        }
        if (xmlName != null) {
            InputStream xmlInputStream = repositoryService.getResourceAsStream(deploymentId, xmlName);
            File file=new File(path+"\\download\\"+xmlName);
            if(!file.exists()){
                boolean mkdirs = file.getParentFile().mkdirs();
                boolean newFile = file.createNewFile();
            }
            OutputStream xmlOutputStream = new FileOutputStream(file);
            IOUtils.copy(xmlInputStream, xmlOutputStream);
            xmlOutputStream.close();
            xmlInputStream.close();
        }

    }
}
