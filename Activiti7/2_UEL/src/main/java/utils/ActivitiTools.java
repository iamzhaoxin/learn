package utils;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/20 21:05
 */
public class ActivitiTools {
    private String processKey;
    private final ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;

    public String getProcessKey() {
        return processKey;
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public ActivitiTools() {
        processEngine=ProcessEngines.getDefaultProcessEngine();
    }

    public ActivitiTools(String processKey){
        this.processKey = processKey;
        processEngine=ProcessEngines.getDefaultProcessEngine();
        repositoryService= processEngine.getRepositoryService();
        runtimeService=processEngine.getRuntimeService();
        taskService=processEngine.getTaskService();
        historyService=processEngine.getHistoryService();
    }

    public ProcessDefinition getLatestProcessDefinition() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .processDefinitionKey(processKey)
                .orderByProcessDefinitionVersion()
                .desc()
//                .asc()
                .list();
        return processDefinitionList.get(0);
    }


}
