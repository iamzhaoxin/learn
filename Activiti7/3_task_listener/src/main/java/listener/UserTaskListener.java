package listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/21 18:22
 */
public class UserTaskListener  implements TaskListener {
    /**
     * 指定负责人
     * @param delegateTask 触发监听器的任务
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        // notice 监听all事件时, 判断当前任务是哪个任务, &&判断当前是create(创建时)还是assignment(分配时)还是complete(完成时)触发
        if("创建".equals(delegateTask.getName()) && "create".equals(delegateTask.getEventName())){
            delegateTask.setAssignee("小艺"); //为任务指定执行人
            System.out.println("task: "+delegateTask.getName()+" was allocate to 小艺");
        }

    }
}
