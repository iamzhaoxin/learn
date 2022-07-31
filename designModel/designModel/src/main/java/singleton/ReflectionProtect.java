package singleton;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过反射的方法创建实例
 * 饿汉模式 和 静态内部类，构造函数只是私有化了，仍可以调用，可以通过反射破坏单例模式
 *  - 通过在构造函数里抛出异常，阻止破坏单例
 *
 * @Author: 赵鑫
 * @Date: 2022/7/31
 */
public class ReflectionProtect {

    @Test
    public void reflectDestroySingleton() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<NormalHungrySingleton> constructor = NormalHungrySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        NormalHungrySingleton normalHungrySingleton = constructor.newInstance();
        NormalHungrySingleton singleton=NormalHungrySingleton.getInstance();
        System.out.println(normalHungrySingleton==singleton);
    }

    @Test
    public void reflectProtectedSingleton() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Constructor<ProtectedHungrySingleton> constructor = ProtectedHungrySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ProtectedHungrySingleton normalHungrySingleton = constructor.newInstance();
        ProtectedHungrySingleton singleton=ProtectedHungrySingleton.getInstance();
        System.out.println(normalHungrySingleton==singleton);
    }


}

class NormalHungrySingleton{
    private static final NormalHungrySingleton instance=new NormalHungrySingleton();
    private NormalHungrySingleton(){}
    public static NormalHungrySingleton getInstance(){
        return instance;
    }
}

class ProtectedHungrySingleton{

    private static class innerClassHolder{
        private static ProtectedHungrySingleton instance=new ProtectedHungrySingleton();
    }
    private ProtectedHungrySingleton(){
        throw new RuntimeException("只能创建一个实例");
    }
    public static ProtectedHungrySingleton getInstance(){
        return innerClassHolder.instance;
    }
}
