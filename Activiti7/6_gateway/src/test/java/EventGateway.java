/**
 * @Author: 赵鑫
 * @Date: 2022/3/4 16:54
 */

/*
    notice
        事件网关：专门为中间捕获事件设置的，它允许设置多个输出流指向多个不同的中间捕获事件(最少两个)。在流程执行到事件网关后，流程处于“等待”状态。等待捕获事件的触发。
        例如如下流程,当流程启动后，便在信号捕获事件和定时捕获事件处处于等待，如果在10秒内没接收到信号testsignal，事件网关自动销毁了信号捕获事件，执行用户任务1节点，用户任务2节点就不再触发。
        https://cxyzjd.com/article/qq_39099625/115730664
 */
public class EventGateway {
}
