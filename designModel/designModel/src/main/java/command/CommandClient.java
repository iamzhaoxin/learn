package command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Invoker接收并调用命令对象，命令对象中持有Receiver（去执行命令）和执行命令需要的参数
 * (用一个队列 解耦生产者和消费者）
 * @Author: 赵鑫
 * @Date: 2022/8/26
 */
public class CommandClient {

    public static void main(String[] args) {
        Order order = new Order("炒饭");
        Receiver cooker = new Receiver();
        ICommand command=new OrderCommand(cooker,order);    // 命令对象
        Invoker invoker=new Invoker(command);
        invoker.call();
    }
}

@Data
@AllArgsConstructor
class Order{
    private String orderFoodName;
}

// Receiver 真正执行命令（厨师类
class Receiver {
    public void action(String name){
        System.out.println("制作食物："+name);
    }
}

// Command 抽象命令类
interface ICommand{
    void execute();
}

// Concrete Command 具体命令类
class OrderCommand implements ICommand{

    // 持有Receiver对象
    private Receiver receiver;
    // 执行命令的其他参数
    private Order order;

    public OrderCommand(Receiver receiver, Order order) {
        this.receiver = receiver;
        this.order = order;
    }

    @Override
    public void execute() {
        System.out.println("即将开始做："+order.getOrderFoodName());
        receiver.action(order.getOrderFoodName());
    }
}

// Invoker 请求/调用者 (服务员）
class Invoker {
    private ICommand command;

    public Invoker(ICommand command) {
        this.command = command;
    }

    public void call(){
        command.execute();
    }
}


