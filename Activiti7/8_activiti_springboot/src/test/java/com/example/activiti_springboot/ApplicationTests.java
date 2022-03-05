package com.example.activiti_springboot;

import com.example.activiti_springboot.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)    //IDEA 中可以省略不写，其他IDE要写
@SpringBootTest
class ApplicationTests {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil security;

    /**
     * 查看流程定义内容
     */
    @Test
    void findProcess() {
        security.logInAs("jack");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("可用流程定义的总数：{}", processDefinitionPage.getTotalItems());

        for (ProcessDefinition process : processDefinitionPage.getContent()) {
            log.info("流程定义内容：{}", process);
        }
    }

    /**
     * 启动流程
     */
    @Test
    void startProcess() {
        security.logInAs("jack");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("myDemo").build());
        log.info("流程实例内容：{}", processInstance);
    }

    /**
     * 执行任务
     */
    @Test
    void completeTask() {
        //登录用户
        security.logInAs("other");
        //查询任务
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        // notice : tasks.getTotalItems()>0
        if (tasks != null && tasks.getTotalItems()>0) {
            for (Task task : tasks.getContent()) {
                taskRuntime.claim(
                        TaskPayloadBuilder.claim().withTaskId(task.getId()).build()
                );
                log.info("任务内容："+task);
                taskRuntime.complete(
                        TaskPayloadBuilder.complete().withTaskId(task.getId()).build()
                );
            }
        }else{
            log.info("no task");
        }

    }

}
