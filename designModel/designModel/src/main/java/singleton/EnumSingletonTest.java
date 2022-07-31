package singleton;

import java.util.LinkedList;

/**
 * 枚举方式
 *  - 线程安全，只会被装载一次
 *  - 不会被反射破坏，
 *  - 天然可序列化
 *      - 在序列化枚举类型时，只会存储枚举类的”引用”和枚举常量的名称。
 *          - 在同一个JVM环境下，反序列化时，这些信息用来查找JVM里存在的对象，天然实现单例
 *          - 不用JVM中进行反序列化，可能会得到不同的hashcode。
 *            但单例对象的重点是，同一个JVM不能有多余的实例。枚举类型的序列化机制保证了只会查找已存在的枚举类型实例，而不创建
 *
 * @Author: 赵鑫
 * @Date: 2022/7/31
 */
public class EnumSingletonTest {
    public static void main(String[] args) {
        EnumSingleton singleton1=EnumSingleton.INSTANCE;
        EnumSingleton singleton2=EnumSingleton.getInstance();
        System.out.println(singleton2==singleton1);
    }
}

enum EnumSingleton{
    // EnumSingleton对象
    INSTANCE;
    // 可以有私有属性
    private String privateProperty;
    //可以有公有属性
    public String publicProperty;
    // 方法
    public void method(){
        System.out.println("any method");
    }
    // 静态代码块
    static {
        System.out.println("静态代码块");
    }
    // 私有构造方法
    private EnumSingleton(){
        privateProperty="privateProperty";
        publicProperty="publicProperty";
    }
    // 返回Singleton对象
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
