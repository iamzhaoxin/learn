package singleton;

/**
 * 2. 饿汉模式
 * 类加载的“初始化”阶段完成实例的初始化。借助jvm类加载机制保证实例的唯一
 * 1. 静态变量
 * 2. 静态代码块
 * 类加载过程：
 * 1. 加载二进制到内存，生成class
 * 2. 连接：验证、准备（给静态成员变量赋默认值）、解析
 * 3. 初始化：给静态变量赋初值
 * - 成员变量：类里面，方法体之外
 * - 变量：类里面，方法体里面或外面
 * 只有真正使用一个类时，才会触发初始化
 * - 如：当前类是启动类、new操作、访问静态属性或方法、用反射访问类、初始化一个类的子类、等
 *
 * @Author: 赵鑫
 * @Date: 2022/7/21
 */

public class HungrySingleton {
    private static final HungrySingleton instance = new HungrySingleton();
    private HungrySingleton() {
    }
    public HungrySingleton getInstance() {
        return instance;
    }
}

class HungrySingleton2{
    private static final HungrySingleton2 instance;
    private HungrySingleton2() {
    }
    static {
        instance=new HungrySingleton2();
    }
    public HungrySingleton2 getInstance() {
        return instance;
    }
}