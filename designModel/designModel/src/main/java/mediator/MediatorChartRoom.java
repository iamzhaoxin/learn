package mediator;

import lombok.Data;

import java.util.Date;

/**
 * 每个User不互相通话，通过CharRoom作为中介转发
 *
 * @Author: 赵鑫
 * @Date: 2022/8/30
 */
public class MediatorChartRoom {

    public static void showMessage(User user, String message) {
        System.out.println(new Date() + " [" + user.getName() + "] : " + message);
    }
}

@Data
class User {
    private String name;

    public String getName() {
        return name;
    }

    public User(String name){
        this.name  = name;
    }

    public void sendMessage(String message){
        MediatorChartRoom.showMessage(this,message);
    }
}

class MediatorPatternDemo {
    public static void main(String[] args) {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");
    }
}