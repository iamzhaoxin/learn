package basic.thread;

import static java.lang.Thread.sleep;

@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class Phone {
    //默认false：等待接听电话
    private boolean flag = false;

    public static void main(String[] args) {
        Phone huawei = new Phone();
        huawei.run();
    }

    public void run() {

        new Thread(() -> {
            try {
                while (true) {
                    /*
                    如果用this作为锁对象，很容易崩
                    因为this是指的是匿名内部Runnable类，而不是被共享的Phone对象
                     */
                    synchronized (Phone.this) {
                        if (flag) {     //正在接听电话（逻辑上用不到这部分代码）
                            System.out.println(Thread.currentThread().getName() + " 通话中(异常状态)");
                        } else {
                            System.out.println("有电话接入");
                            flag = true;
                        }
                        Phone.this.notify();
                        Phone.this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "等待接听状态").start();

        new Thread(() -> {
            try {
                while (true) {
                    synchronized (Phone.this) {
                        if (flag) {     //正在接听电话
                            System.out.println("通话中……挂断电话");
                            //noinspection BusyWait
                            sleep(1000);
                            flag = false;
                        } else {
                            System.out.println("等待接听中");
                        }
                        Phone.this.notify();
                        Phone.this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "正在接听状态").start();
    }
}
